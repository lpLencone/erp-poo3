<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Verifica se a sessão está nula (usuário não está logado)
    if (session == null || session.getAttribute("userRole") == null) {
        response.sendRedirect("/erp/login.jsp");
        return;
    }

    // Obtém o papel do usuário
    String userRole = session.getAttribute("userRole").toString();

    // Se for cliente, redireciona direto para listagem de produtos
    if ("Cliente".equals(userRole)) {
        response.sendRedirect("/erp/ProductListServlet");
        return;
    }
%>


<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Painel</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Mostrar logado como</h1>

    <!-- Verifica se o usuário é admin (gerente) -->
    <% if ("Administrador".equals(userRole)) { %>
        <p><a href="/erp/cadastroUsuario.jsp">Cadastrar Funcionario</a></p>
    <% } %>

    <!-- Verifica se o usuário é admin ou funcionário -->
    <% if ("Administrador".equals(userRole) || "Funcionario".equals(userRole) || "Gerente".equals(userRole)) { %>
        <p><a href="/erp/cadastroCategoria.jsp">Cadastrar Categoria</a></p>
        <p><a href="/erp/cadastroProduto.jsp">Cadastrar Produto</a></p>
    <% } %>

    <!-- Verifica se o usuário é cliente -->
    <% if ("Cliente".equals(userRole)) { %>
        <p><a href="/erp/fazerPedido.jsp">Fazer Pedido</a></p>
    <% } %>

</body>
</html>
