<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="erp.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
    boolean editing = (product != null && product.id > 0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= editing ? "Edit" : "New" %> Product</title>
</head>
<body>
    <h2><%= editing ? "Edit" : "New" %> Product</h2>
    <form action="ProductServlet" method="post">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="id" value="<%= editing ? product.id : 0 %>">
        <p>Name: <input type="text" name="name" value="<%= editing ? product.name : "" %>" required></p>
        <p>Description: <input type="text" name="description" value="<%= editing ? product.description : "" %>"></p>
        <p>Price: <input type="number" name="price" step="0.01" value="<%= editing ? product.price : 0.0 %>" required></p>
        <p>Stock: <input type="number" name="stock" value="<%= editing ? product.stock : 0 %>" required></p>
        <p>Category Id: <input type="number" name="categoryId" value="<%= editing ? product.categoryId : 0 %>" required></p>
        
        <p><button type="submit">Save</button></p>
    </form>
    <p><a href="ProductServlet">Back to list</a></p>
</body>
</html>