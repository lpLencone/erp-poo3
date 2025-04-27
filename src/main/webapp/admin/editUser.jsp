<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, erp.dao.*, erp.model.*, java.sql.*" %>
<%
    session = request.getSession(false);
    String loggedRole = (String) session.getAttribute("userRole");

    int userId = Integer.parseInt(request.getParameter("id"));

    UserDAO userDAO = new UserDAO();
    User user = null;
    String userRole = null;
    List<String> availableRoles = new ArrayList<>();

    try {
        // Buscar o usuário que será editado
        Connection conn = erp.util.DatabaseConnection.getConnection();
        String sql = "SELECT u.id, u.name, u.email, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.id = rs.getInt("id");
                user.name = rs.getString("name");
                user.email = rs.getString("email");
                userRole = rs.getString("role_name");
            }
        }

        // Verificar se quem está logado PODE editar este usuário
        if (loggedRole.equals("Administrador")) {
            // Admin pode editar qualquer um
        } else if (loggedRole.equals("Gerente")) {
            if (!(userRole.equals("Funcionario") || userRole.equals("Cliente"))) {
                out.println("<h2>Você não tem permissão para editar este tipo de usuário.</h2>");
                return;
            }
        } else {
            out.println("<h2>Acesso negado.</h2>");
            return;
        }

        // Buscar papéis disponíveis para seleção
        sql = "SELECT name FROM roles";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String roleName = rs.getString("name");

                if (loggedRole.equals("Administrador")) {
                    // Admin pode mudar para Admin, Gerente ou Funcionário
                    if (roleName.equals("Administrador") || roleName.equals("Gerente") || roleName.equals("Funcionario")) {
                        availableRoles.add(roleName);
                    }
                } else if (loggedRole.equals("Gerente")) {
                    // Gerente pode mudar para Funcionário ou Cliente
                    if (roleName.equals("Funcionario") || roleName.equals("Cliente")) {
                        availableRoles.add(roleName);
                    }
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        out.println("<h2>Erro ao carregar dados do usuário.</h2>");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Usuário</title>
</head>
<body>
    <h2>Editar Usuário</h2>

    <form action="EditUserServlet" method="post">
        <input type="hidden" name="id" value="<%= user.id %>">

        <label for="name">Nome:</label><br>
        <input type="text" id="name" name="name" value="<%= user.name %>" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" value="<%= user.email %>" required><br><br>

        <label for="role">Tipo de Usuário:</label><br>
        <select name="role" id="role" required>
            <% for (String roleName : availableRoles) { %>
                <option value="<%= roleName %>" <%= roleName.equals(userRole) ? "selected" : "" %>><%= roleName %></option>
            <% } %>
        </select><br><br>

        <input type="submit" value="Salvar Alterações">
    </form>

    <br><a href="userManagement.jsp">Voltar</a>
</body>
</html>
