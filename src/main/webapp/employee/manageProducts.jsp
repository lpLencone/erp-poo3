<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="erp.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
</head>
<body>
    <h2>Products</h2>
    <a href="ProductServlet?action=new">+ Add New Product</a>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Stock</th><th>Actions</th>
        </tr>
        <%
            if (products != null) {
                for (Product p : products) {
        %>
        <tr>
            <td><%= p.id %></td>
            <td><%= p.name %></td>
            <td><%= p.description %></td>
            <td><%= p.price %></td>
            <td><%= p.stock %></td>
            <td>
                <a href="ProductServlet?action=edit&id=<%= p.id %>">Editar</a> |
                <a href="ProductServlet?action=delete&id=<%= p.id %>" onclick="return confirm('Deletar este produto?');">Deletar</a>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr><td colspan="8">No products found.</td></tr>
        <% } %>
    </table>
</body>
</html>