<%@ page import="erp.model.CartItem, erp.model.Product, java.util.List" %>
<%
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seu Carrinho</title>
    <style>
        .cart-item {
            border: 1px solid #ccc;
            padding: 1em;
            margin-bottom: 1em;
            border-radius: 5px;
        }
        .product-name {
            font-weight: bold;
            font-size: 1.2em;
        }
        .quantity-controls {
            display: flex;
            align-items: center;
            gap: 0.5em;
            margin: 0.5em 0;
        }
        .quantity-controls form {
            display: inline;
        }
        .total {
            font-weight: bold;
            font-size: 1.1em;
            margin-top: 1em;
        }
    </style>
</head>
<body>
    <h2>Seu Carrinho</h2>

    <% 
    double total = 0;
    if (cart != null && !cart.isEmpty()) {
        for (int i = 0; i < cart.size(); i++) {
            CartItem item = cart.get(i);
            total += item.getSubtotal();
    %>
    <%
        String cartMessage = (String) session.getAttribute("cartMessage");
        if (cartMessage != null) {
    %>
        <div style="color: red;"><%= cartMessage %></div>
    <%
            session.removeAttribute("cartMessage");
        }
    %>
        <div class="cart-item">
            <div class="product-name"><%= item.product.name %></div>
            <div>Preço: R$ <%= String.format("%.2f", item.product.price) %></div>
            <div>Subtotal: R$ <%= String.format("%.2f", item.getSubtotal()) %></div>
            
            <div class="quantity-controls">
                <form action="/erp/UpdateCartServlet" method="post">
                    <input type="hidden" name="action" value="decrease">
                    <input type="hidden" name="index" value="<%= i %>">
                    <button type="submit">-</button>
                </form>

                <span><%= item.quantity %></span>

                <form action="/erp/UpdateCartServlet" method="post">
                    <input type="hidden" name="action" value="increase">
                    <input type="hidden" name="index" value="<%= i %>">
                    <button type="submit">+</button>
                </form>
            </div>

            <form action="/erp/RemoveFromCartServlet" method="post">
                <input type="hidden" name="index" value="<%= i %>">
                <button type="submit">Remover</button>
            </form>
        </div>
    <% 
        }
    %>
        <div class="total">Total: R$ <%= String.format("%.2f", total) %></div>

        <form action="/erp/CheckoutServlet" method="post">
            <button type="submit">Finalizar Compra</button>
        </form>
    <% 
    } else { 
    %>
        <p>Seu carrinho está vazio.</p>
    <% } %>

    <br>
    <a href="/erp/ProductListServlet">Continuar comprando</a>
</body>
</html>
