<%@ page import="erp.dao.ProductDAO, java.util.Map, erp.model.Product" %>
<%
	ProductDAO productDAO = new ProductDAO();
    Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
    double total = 0;
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
    if (cart != null && !cart.isEmpty()) {
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            
            Product product = productDAO.getProductById(productId);  // Supondo que você tenha um método para recuperar o produto pelo ID
            if (product != null) {
                double subtotal = product.price * quantity;
                total += subtotal;
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
            <div class="product-name"><%= product.name %></div>
            <div>Preço: R$ <%= String.format("%.2f", product.price) %></div>
            <div>Subtotal: R$ <%= String.format("%.2f", subtotal) %></div>

            <div class="quantity-controls">
                <form action="UpdateCartServlet" method="post">
                    <input type="hidden" name="action" value="decrease">
                    <input type="hidden" name="productId" value="<%= productId %>">
                    <button type="submit" <%= quantity == 1 ? "disabled" : "" %>>-</button>
                </form>

                <span><%= quantity %></span>

                <form action="UpdateCartServlet" method="post">
                    <input type="hidden" name="action" value="increase">
                    <input type="hidden" name="productId" value="<%= productId %>">
                    <button type="submit" <%= quantity == product.stock ? "disabled" : "" %>>+</button>
                </form>
            </div>

            <form action="RemoveFromCartServlet" method="post">
                <input type="hidden" name="productId" value="<%= productId %>">
                <button type="submit">Remover</button>
            </form>
        </div>
    <% 
            }
        }
    %>
        <div class="total">Total: R$ <%= String.format("%.2f", total) %></div>

        <form action="CheckoutServlet" method="post">
            <button type="submit">Finalizar Compra</button>
        </form>
    <% 
    } else { 
    %>
        <p>Seu carrinho está vazio.</p>
    <% } %>

    <br>
    <a href="ProductListServlet">Continuar comprando</a>
</body>
</html>
