<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, erp.dao.ProductDAO, erp.model.Product" %>
<%
    String userRole = session.getAttribute("userRole").toString();
	String username = session.getAttribute("username").toString();
	
    ProductDAO productDAO = new ProductDAO();
    List<Product> products = productDAO.getAllProducts();

    List<Product> lowStockProducts = new ArrayList<>();
    for (Product p : products) {
        if (p.stock < 10) {
            lowStockProducts.add(p);
        }
    }
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Painel</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Painel</h1>
    
    <% if (!lowStockProducts.isEmpty()) { %>
	    <div style="color: red; font-weight: bold;">
	        <% if (lowStockProducts.size() == 1) { %>
	            Aviso: O produto "<%= lowStockProducts.get(0).name %>" está com estoque baixo!
	        <% } else { %>
	            Aviso: Existem <%= lowStockProducts.size() %> produtos com estoque baixo!
	        <% } %>
	    </div>
	<% } %>
	    

    <p>Logado como: <%= username %> (<%= userRole %>)</p>

    <% if ("Administrador".equals(userRole)) { %>
    <p><a href="/erp/admin/ListLogsServlet">Ver Logs</a></p> 
    <% } %>

    <% if (!("Funcionario".equals(userRole))) { %>
    <p><a href="/erp/admin/userManagement.jsp">Gerenciar Usuários</a></p>
    <p><a href="/erp/admin/manageSuppliers.jsp">Gerenciar Fornecedores</a></p>
    <p><a href="/erp/admin/manageCategories.jsp">Gerenciar Categorias</a></p>
    <% } %>

    <p><a href="productManagement.jsp">Gerenciar Produtos</a></p>

    <p><a href="/erp/LogoutServlet">Logout</a></p>
</body>
</html>
