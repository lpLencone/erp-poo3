<%@ page import="erp.model.Category" %>
<%
    Category category = (Category) request.getAttribute("category");
    boolean editing = category != null && category.id > 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= editing ? "Edit" : "Add" %> Category</title>
</head>
<body>
    <h2><%= editing ? "Edit" : "Add" %> Category</h2>
    <form action="CategoryServlet" method="post">
        <% if (editing) { %>
            <input type="hidden" name="id" value="<%= category.id %>">
        <% } %>
        <label>Name:</label>
        <input type="text" name="name" value="<%= editing ? category.name : "" %>" required>
        <br><br>
        <input type="submit" value="Save">
    </form>
    <br>
    <a href="CategoryServlet">Back to list</a>
</body>
</html>