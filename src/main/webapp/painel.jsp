<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Verifica se a sessão está nula (usuário não está logado)
    if (session == null || session.getAttribute("userRole") == null) {
        // Redireciona para a página de login
        response.sendRedirect("/erp/login.jsp");
        return;  // Impede que o restante da página seja processado
    }

    // Obtém o papel do usuário da sessão
    String userRole = session.getAttribute("userRole").toString();
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Painel</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Bem-vindo ao sistema!</h1>

    <!-- Verifica se o usuário é admin (gerente) -->
    <% if ("Administrador".equals(userRole)) { %>
        <p><a href="/erp/cadastroUsuario.jsp">Cadastrar Funcionario</a></p>
    <% } %>

    <!-- Verifica se o usuário é admin ou funcionário -->
    <% if ("Administrador".equals(userRole) || "Funcionario".equals(userRole)) { %>
        <p><a href="/erp/cadastroCategoria.jsp">Cadastrar Categoria</a></p>
        <p><a href="/erp/cadastroCliente.jsp">Cadastrar Cliente</a></p>
        <p><a href="/erp/cadastroProduto.html">Cadastrar Produto</a></p>
    <% } %>

    <!-- Verifica se o usuário é cliente -->
    <% if ("Cliente".equals(userRole)) { %>
        <p><a href="/erp/fazerPedido.jsp">Fazer Pedido</a></p>
    <% } %>

</body>
</html>
