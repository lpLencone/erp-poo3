<%@ page import="java.sql.*" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Funcionário</title>
</head>
<body>
    <h2>Cadastro de Funcionário</h2>

    <%
        String userRole = (String) session.getAttribute("userRole");
    %>

    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <form action="/erp/RegisterEmployeeServlet" method="post">
        <label for="name">Nome:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="password">Senha:</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <label for="role">Papel:</label><br>
        <select id="role" name="role_id" required>
            <%
                try {
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost/erp", "postgres", "admin");
                    PreparedStatement statement;

                    if (userRole.equals("Administrador")) {
                        // Admin pode ver gerente e funcionário
                        statement = connection.prepareStatement("SELECT id, name FROM roles WHERE name IN ('Gerente', 'Funcionario')");
                    } else if (userRole.equals("Gerente")) {
                        // Gerente só pode ver funcionário
                        statement = connection.prepareStatement("SELECT id, name FROM roles WHERE name = 'Funcionario'");
                    } else {
                        statement = null;
                        out.println("<option disabled>Sem permissão para cadastrar usuários</option>");
                    }

                    if (statement != null) {
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                            int roleId = resultSet.getInt("id");
                            String roleName = resultSet.getString("name");
            %>
                            <option value="<%= roleId %>"><%= roleName %></option>
            <%
                        }
                        resultSet.close();
                        statement.close();
                    }
                    connection.close();
                } catch (Exception e) {
                    out.println("<option disabled>Erro ao carregar papéis</option>");
                    e.printStackTrace(new java.io.PrintWriter(out));
                }
            %>
        </select><br><br>

        <input type="submit" value="Cadastrar Usuário">
    </form>

    <p><a href="/erp/adminPanel.jsp">Voltar para o Painel</a></p>
</body>
</html>
