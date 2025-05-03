<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, erp.dao.*, erp.model.*" %>
<%
    session = request.getSession(false);
    String userRole = (String) session.getAttribute("userRole");

    int editUserId = Integer.parseInt(request.getParameter("id"));

    UserDAO userDAO = new UserDAO();
    RoleDAO roleDAO = new RoleDAO();
    User editUser = userDAO.getUserById(editUserId);
    String editUserRole = roleDAO.getRoleById(editUser.roleId).name;

    // Verificar se quem está logado PODE editar este usuário
    if (userRole.equals(editUserRole)) {
        out.println("<h2>Você não tem permissão para editar este tipo de usuário.</h2>");
        return;
    }

    List<String> availableRoles = new ArrayList<>();
    if (userRole.equals("Administrador")) {
        availableRoles.add("Gerente");
    }
    availableRoles.add("Funcionario");

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
        <input type="hidden" name="id" value="<%= editUser.id %>">

        <label for="name">Nome:</label><br>
        <input type="text" id="name" name="name" value="<%= editUser.name %>" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" value="<%= editUser.email %>" required><br><br>

        <label for="role">Tipo de Usuário:</label><br>
        <select name="role" id="role" required>
            <% for (String roleName : availableRoles) { %>
                <option	value="<%= roleName %>" 
                		<%= roleName.equals(editUserRole) ? "selected" : "" %>>
                	<%= roleName %>
                </option>
            <% } %>
        </select><br><br>

        <input type="submit" value="Salvar Alterações">
    </form>

    <br><a href="userManagement.jsp">Voltar</a>
</body>
</html>
