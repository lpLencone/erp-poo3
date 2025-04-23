<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="erp.model.Supplier" %>
<%
    Supplier supplier = (Supplier) request.getAttribute("supplier");
%>

<html>
<head>
    <title>Editar Fornecedor</title>
</head>
<body>
    <h1>Editar Fornecedor</h1>
    <form action="SupplierServlet" method="post">
        <input type="hidden" name="id" value="<%= supplier.id %>"/>
        <label for="name">Nome:</label>
        <input type="text" id="name" name="name" value="<%= supplier.name %>"/>
        <input type="submit" value="Salvar"/>
    </form>
    <a href="suppliers.jsp">Cancelar</a>
</body>
</html>