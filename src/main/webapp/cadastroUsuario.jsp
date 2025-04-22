<%@ page import="java.sql.*" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Funcionario</title>
</head>
<body>
    <h2>Cadastro de Funcionario</h2>

    <%
        Integer roleId = (Integer) session.getAttribute("role");
        if (roleId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    
    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <form action="/erp/CadastrarUsuarioServlet" method="post">
        <label for="nome">Nome:</label><br>
        <input type="text" id="nome" name="nome" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="senha">Senha:</label><br>
        <input type="password" id="senha" name="senha" required><br><br>

        <label for="role">Papel:</label><br>
        <select id="role" name="role_id" required>
            <%
                try {
                    Class.forName("org.postgresql.Driver");
                    Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost/erp", "postgres", "admin");
                    PreparedStatement stmt;

                    if (roleId == 1) {
                        // Admin pode ver gerente e funcion�rio
                        stmt = conn.prepareStatement("SELECT id, name FROM roles WHERE name IN ('Gerente', 'Funcionario')");
                    } else if (roleId == 2) {
                        // Gerente s� pode ver funcion�rio
                        stmt = conn.prepareStatement("SELECT id, name FROM roles WHERE name = 'Funcionario'");
                    } else {
                        stmt = null;
                        out.println("<option disabled>Sem permiss�o para cadastrar usu�rios</option>");
                    }

                    if (stmt != null) {
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String nome = rs.getString("name");
            %>
                            <option value="<%= id %>"><%= nome %></option>
            <%
                        }
                        rs.close();
                        stmt.close();
                    }
                    conn.close();
                } catch (Exception e) {
                    out.println("<option disabled>Erro ao carregar pap�is</option>");
                    e.printStackTrace(new java.io.PrintWriter(out));
                }
            %>
        </select><br><br>

        <input type="submit" value="Cadastrar Usu�rio">
    </form>

    <p><a href="/erp/painel.jsp">Voltar para o Painel</a></p>
</body>
</html>
