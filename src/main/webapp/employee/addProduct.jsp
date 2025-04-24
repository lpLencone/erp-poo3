<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="erp.dao.CategoryDAO, erp.model.Category, java.util.List" %>
<%
    CategoryDAO categoryDAO = new CategoryDAO();
    List<Category> categories = categoryDAO.getAllCategories();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Adicionar Produto</title>
</head>
<body>
    <h1>Adicionar Novo Produto</h1>

    <form action="AddProductServlet" method="post">
        <label for="name">Nome:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="price">Preço:</label>
        <input type="number" id="price" name="price" step="0.01" required><br><br>

        <label for="categoryId">Categoria:</label>
        <select id="categoryId" name="categoryId" required>
            <% for (Category category : categories) { %>
                <option value="<%= category.id %>"><%= category.name %></option>
            <% } %>
        </select><br><br>

        <label for="stock">Estoque:</label>
        <input type="number" id="stock" name="stock" required><br><br>

        <input type="submit" value="Adicionar Produto">
    </form>

    <br>
    <a href="productManagement.jsp">Voltar à Gestão de Produtos</a>
</body>
</html>
