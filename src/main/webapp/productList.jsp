<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="erp.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos</title>
</head>
<body>
    <h2>Produtos Disponíveis</h2>

    <table border="1">
        <tr>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Preço</th>
            <th>Estoque</th>
            <th>Ação</th>
        </tr>
        <% if (products != null) {
            for (Product p : products) {
        %>
            <tr>
                <td><%= p.name %></td>
                <td><%= p.description %></td>
                <td>R$ <%= String.format("%.2f", p.price) %></td>
                <td><%= p.stock %></td>
                <td>
                    <form action="/erp/AddToCartServlet" method="post" style="margin: 0;">
                        <input type="hidden" name="productId" value="<%= p.id %>"/>
                        <button type="submit">Adicionar ao Carrinho</button>
                    </form>
                </td>
            </tr>
        <% 
            }
        } else { %>
            <tr><td colspan="5">Nenhum produto encontrado.</td></tr>
        <% } %>
    </table>

    <p><a href="viewCart.jsp">Ver Carrinho</a> | <a href="logout.jsp">Sair</a></p>
</body>
</html>
