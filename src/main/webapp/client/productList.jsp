<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="erp.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String errorMessage = (String) request.getAttribute("errorMessage");
    Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
    if (cart == null) {
        cart = new java.util.HashMap<>();
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos</title>
</head>
<body>
    <h2>Produtos Disponíveis</h2>

    <% String message = (String) session.getAttribute("cartMessage");
       if (message != null) { %>
        <p style="color: green;"><%= message %></p>
    <% session.removeAttribute("cartMessage"); } %>

    <% if (errorMessage != null) { %>
        <p style="color: red;"><%= errorMessage %></p>
    <% } %>

    <table border="1">
        <tr>
            <th>Nome</th>
            <th>Preço</th>
            <th>Estoque</th>
            <th>Ação</th>
        </tr>
        <% if (products != null) {
            for (Product p : products) {
                int currentQty = cart.getOrDefault(p.id, 0);
        %>
            <tr>
                <td><%= p.name %></td>
                <td>R$ <%= String.format("%.2f", p.price) %></td>
                <td><%= p.stock %></td>
                <td>
                    <form action="AddToCartServlet" method="post" style="margin: 0;" onsubmit="return prepareQuantity(this)">
                        <input type="hidden" name="productId" value="<%= p.id %>"/>
                        <div style="display: flex; align-items: center; gap: 4px;">
                            <button type="button" onclick="adjustQuantity(this, -1, <%= p.stock %>)" <%= p.stock == 0 ? "disabled" : "" %>>−</button>
                            <input type="text" name="displayQuantity" value="<%= currentQty %>" readonly style="width: 30px; text-align: center;" />
                            <button type="button" onclick="adjustQuantity(this, 1, <%= p.stock %>)" <%= p.stock == 0 ? "disabled" : "" %>>+</button>
                        </div>
                        <input type="hidden" name="quantity" value="<%= currentQty %>" />
                        <br/>
                        <button type="submit" <%= p.stock == 0 ? "disabled" : "" %>>Atualizar</button>
                    </form>
                </td>
            </tr>
        <% } } else { %>
            <tr><td colspan="4">Nenhum produto encontrado.</td></tr>
        <% } %>
    </table>

    <p><a href="viewCart.jsp">Ver Carrinho</a></p>
    <p><a href="/erp/LogoutServlet">Sair</a></p>

    <script>
        function adjustQuantity(button, delta, maxStock) {
            const form = button.closest("form");
            const displayInput = form.querySelector("input[name='displayQuantity']");
            const hiddenInput = form.querySelector("input[name='quantity']");

            let current = parseInt(displayInput.value, 10);
            if (isNaN(current)) current = 0;

            const newValue = Math.max(0, Math.min(current + delta, maxStock));  // Garantir que a quantidade esteja entre 0 e o estoque máximo
            displayInput.value = newValue;
            hiddenInput.value = newValue;
        }

        function prepareQuantity(form) {
            const displayValue = form.querySelector("input[name='displayQuantity']").value;
            form.querySelector("input[name='quantity']").value = displayValue;
            return true;
        }
    </script>
</body>
</html>
