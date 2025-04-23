<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String userRole = session.getAttribute("userRole").toString();
    if ("Cliente".equals(userRole)) {
        response.sendRedirect("/erp/ProductListServlet");
        return;
    }
    String username = (String) session.getAttribute("username");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Painel</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Painel</h1>

    <p>Logado como: <strong><%= username != null ? username : "Usuário Desconhecido" %> (<%= userRole %>)</strong></p>

    <% if ("Administrador".equals(userRole) || "Gerente".equals(userRole)) { %>
        <p><a href="/erp/registerEmployee.jsp">Cadastrar Funcionário</a></p>
        <p><a href="/erp/userManagement.jsp">Gerenciar Usuários</a></p>
    <% } %>

    <% if ("Administrador".equals(userRole) || "Gerente".equals(userRole) || "Funcionario".equals(userRole)) { %>
        <p><a href="/erp/registerCategory.jsp">Cadastrar Categoria</a></p>
        <p><a href="/erp/registerProduct.jsp">Cadastrar Produto</a></p>
    <% } %>
    
    <p><a href="/erp/LogoutServlet">Logout</a></p>
</body>
</html>
