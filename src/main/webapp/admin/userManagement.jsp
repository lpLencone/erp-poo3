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

    <form action="UserListServlet" method="get">
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
        // Exibindo mensagem de erro, se existir
        String errorMessage = request.getParameter("error");
        if (errorMessage != null) { 
    %>
        <p style="color:red;">Erro: <%= errorMessage %></p>
    <% 
        }

        List<User> users = (List<User>) request.getAttribute("users");
        String filter = (String) request.getAttribute("roleFilter");

        if (users != null && !users.isEmpty()) {
    %>
        <h3>Usuários do tipo: <%= filter %></h3>
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
                        <form action="DeleteUserServlet" method="post" style="display:inline;">
                            <input type="hidden" name="userId" value="<%= user.id %>">
                            <input type="hidden" name="userFilter" value="<%= filter %>">
                            <input type="submit" value="Deletar">
                        </form>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } else if (filter != null) { %>
        <p>Nenhum usuário encontrado para o tipo <strong><%= filter %></strong>.</p>
    <% } %>

    <br><a href="/erp/employee/adminPanel.jsp">Voltar ao Painel</a>
</body>
</html>
