<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="erp.dao.SupplierDAO, erp.model.Supplier, java.util.List" %>
<%
    SupplierDAO dao = new SupplierDAO();
    List<Supplier> suppliers = dao.getAllSuppliers();
%>

<html>
<head>
    <title>Lista de Fornecedores</title>
</head>
<body>
    <h1>Fornecedores</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Ações</th>
        </tr>
        <% for (Supplier supplier : suppliers) { %>
            <tr>
                <td><%= supplier.id %></td>
                <td><%= supplier.name %></td>
                <td>
                    <a href="SupplierServlet?action=edit&id=<%= supplier.id %>">Editar</a>
                </td>
            </tr>
        <% } %>
    </table>
</body>
</html>