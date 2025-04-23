<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, erp.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Usuários</title>
</head>
<body>
<%
    String userRole = (String) session.getAttribute("userRole");
    if (userRole == null) {
        response.sendRedirect("login.html");
        return;
    }

    if (!userRole.equals("Administrador") && !userRole.equals("Gerente")) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
%>
        <h2>401 - Acesso não autorizado</h2>
        <a href="/erp/productList.jsp">Voltar à lista de produtos</a>
<%
        return;
    }
%>
    <h2>Gerenciar Usuários</h2>

    <form action="/erp/UserListServlet" method="get">
        <label for="role">Filtrar por tipo:</label>
        <select name="role" id="role">
            <% if ("Administrador".equals(userRole)) { %>
                <option value="Gerente">Gerente</option>
            <% } %>
            <option value="Funcionario">Funcionário</option>
            <option value="Cliente">Cliente</option>
        </select>
        <input type="submit" value="Buscar">
    </form>

    <hr>

    <%
        List<User> users = (List<User>) request.getAttribute("users");
        String filtro = (String) request.getAttribute("filtro");

        if (users != null && !users.isEmpty()) {
    %>
        <h3>Usuários do tipo: <%= filtro %></h3>
        <table border="1">
            <tr>
                <th>ID</th><th>Nome</th><th>Email</th><th>Ações</th>
            </tr>
            <% for (User user : users) { %>
                <tr>
                    <td><%= user.id %></td>
                    <td><%= user.name %></td>
                    <td><%= user.email %></td>
                    <td>
                        <form action="/erp/DeleteUserServlet" method="post" style="display:inline;">
                            <input type="hidden" name="userId" value="<%= user.id %>">
                            <input type="hidden" name="userType" value="<%= filtro %>">
                            <input type="submit" value="Deletar">
                        </form>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } else if (filtro != null) { %>
        <p>Nenhum usuário encontrado para o tipo <strong><%= filtro %></strong>.</p>
    <% } %>

    <br><a href="/erp/employee/adminPanel.jsp">Voltar ao Painel</a>
</body>
</html>
