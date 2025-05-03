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

    <form action="AddToCartServlet" method="post" 
    	  onsubmit="return prepareAllQuantities();">	
        <table border="1">
            <tr>
                <th>Nome</th>
                <th>Preço</th>
                <th>Estoque</th>
                <th>Quantidade</th>
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
                        <div style="display: flex; align-items: center; gap: 4px;">
                            <button type="button" onclick="adjustQuantity(this, -1, <%= p.stock %>)" <%= p.stock == 0 ? "disabled" : "" %>>−</button>
                            <input type="text" name="displayQuantity_<%= p.id %>" value="<%= currentQty %>" readonly style="width: 30px; text-align: center;" />
                            <button type="button" onclick="adjustQuantity(this, 1, <%= p.stock %>)" <%= p.stock == 0 ? "disabled" : "" %>>+</button>
                        </div>
                        <input type="hidden" name="quantity_<%= p.id %>" value="<%= currentQty %>"/>
                    </td>
                </tr>
            <% } } else { %>
                <tr><td colspan="4">Nenhum produto encontrado.</td></tr>
            <% } %>
        </table>

        <br/>
        <button type="submit">Atualizar Carrinho</button>
    </form>

    <p><a href="viewCart.jsp">Ver Carrinho</a></p>
    <p><a href="purchaseHistory.jsp">Histórico de Compras</a></p>
    <p><a href="/erp/LogoutServlet">Sair</a></p>

    <script>
        function adjustQuantity(button, delta, maxStock) {
            const form = button.closest("form");
            const row = button.closest("tr");
            const displayInput = row.querySelector("input[name^='displayQuantity_']");
            const hiddenInput = row.querySelector("input[name^='quantity_']");

            let current = parseInt(displayInput.value, 10);
            if (isNaN(current)) current = 0;

            const newValue = Math.max(0, Math.min(current + delta, maxStock));
            displayInput.value = newValue;
            hiddenInput.value = newValue;
        }

        function prepareAllQuantities() {
            const inputs = document.querySelectorAll("input[name^='displayQuantity_']");
            inputs.forEach(displayInput => {
                const productId = displayInput.name.split("_")[1];
                const hiddenInput = document.querySelector("input[name='quantity_" + productId + "']");
                hiddenInput.value = displayInput.value;
            });
            return true;
        }
    </script>
</body>

</html>
