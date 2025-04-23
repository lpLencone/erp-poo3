<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="erp.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos</title>
</head>
<body>
    <h2>Produtos Disponíveis</h2>

    <%
	    String message = (String) session.getAttribute("cartMessage");
	    if (message != null) {
	%>
	    <p style="color: red;"><%= message %></p>
	<%
	        session.removeAttribute("cartMessage");
	    }
	%>

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
				        <input type="number"
				               name="quantity"
				               value="<%= p.stock == 0 ? 0 : 1 %>"
				               min="<%= p.stock == 0 ? 0 : 1 %>"
				               max="<%= p.stock %>"
				               style="width: 60px;" />
				        <button type="submit" <%= p.stock == 0 ? "disabled" : "" %>>Adicionar ao Carrinho</button>
				    </form>
				</td>

            </tr>
        <% 
            }
        } else { %>
            <tr><td colspan="5">Nenhum produto encontrado.</td></tr>
        <% } %>
    </table>

    <p><a href="/erp/viewCart.jsp">Ver Carrinho</a> | <a href="/erp/LogoutServlet">Sair</a></p>
</body>
</html>
