<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="erp.model.*, erp.dao.*, java.util.*" %>
<%
    int productId = Integer.parseInt(request.getParameter("id"));
    ProductDAO productDAO = new ProductDAO();
    Product product = productDAO.getProductById(productId);

    CategoryDAO categoryDAO = new CategoryDAO();
    List<Category> categories = categoryDAO.getAllCategories();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Produto</title>
</head>
<body>
    <h1>Editar Produto</h1>

    <form action="UpdateProductServlet" method="post">
        <input type="hidden" name="id" value="<%= product.id %>">

        <label for="name">Nome:</label>
        <input type="text" id="name" name="name" value="<%= product.name %>" required><br><br>

        <label for="price">Preço:</label>
        <input type="number" id="price" name="price" step="0.01" value="<%= product.price %>" required><br><br>

        <label for="category">Categoria:</label>
        <select id="category" name="categoryId" required>
            <% for (Category c : categories) { %>
                <option value="<%= c.id %>" <%= c.id == product.categoryId ? "selected" : "" %>><%= c.name %></option>
            <% } %>
        </select><br><br>

        <label for="stock">Estoque:</label>
        <input type="number" id="stock" name="stock" value="<%= product.stock %>" required><br><br>

        <input type="submit" value="Atualizar Produto">
    </form>

    <br>
    <a href="productManagement.jsp">Voltar à Gestão de Produtos</a>
</body>
</html>
