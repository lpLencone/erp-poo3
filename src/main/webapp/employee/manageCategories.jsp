<%@ page import="erp.model.Category" %>
<%@ page import="java.util.List" %>
<%
    List<Category> categories = (List<Category>) request.getAttribute("categories");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category List</title>
</head>
<body>
    <h2>Categories</h2>
    <a href="CategoryServlet?action=edit">Add New Category</a>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
        <% for (Category c : categories) { %>
        <tr>
            <td><%= c.id %></td>
            <td><%= c.name %></td>
            <td>
                <a href="CategoryServlet?action=edit&id=<%= c.id %>">Edit</a>
                <a href="CategoryServlet?action=delete&id=<%= c.id %>" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>