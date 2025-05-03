<%@ page import="java.util.List" %>
<%@ page import="erp.dao.CategoryDAO" %>
<%@ page import="erp.model.Category" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Gerenciamento de Categorias</title>
</head>
<body>
    <h2>Gerenciar Categoria</h2>

<%
    String message = request.getParameter("message");
    if (message != null) {
        boolean error = message.toLowerCase().contains("erro");
        String cor = error ? "red" : "green";
%>
    <p style="color: <%= cor %>;"><%= message %></p>
<%
    }
%>

    <!-- Formulário de Cadastro -->
    <form action="RegisterCategoryServlet" method="post">
        <label for="name">Nome da Categoria:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <input type="submit" value="Cadastrar">
    </form>

    <h2>Categorias Cadastradas</h2>

<%
    CategoryDAO categoryDAO = new CategoryDAO();
    List<Category> categorias = categoryDAO.getAllCategories();
    if (categorias.isEmpty()) {
%>
    <p>Nenhuma categoria cadastrada.</p>
<%
    } else {
%>
    <table border="1">
        <tr>
            <th>Nome</th>
            <th>Ações</th>
        </tr>
    <% for (Category categoria : categorias) { %>
        <tr>
            <td><%= categoria.name %></td>
            <td>
                <form action="DeleteCategoryServlet" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= categoria.id %>">
                    <input type="submit" value="Excluir" onclick="return confirm('Deseja realmente excluir esta categoria?');">
                </form>
            </td>
        </tr>
    <% } %>
    </table>
<%
    }
%>

    <p><a href="/erp/employee/adminPanel.jsp">Voltar ao Painel</a></p>
</body>
</html>
