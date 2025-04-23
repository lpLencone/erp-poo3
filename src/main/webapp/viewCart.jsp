<%@ page import="erp.model.CartItem, erp.model.Product, java.util.List" %>
<%
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Cart</title>
</head>
<body>
    <h2>Shopping Cart</h2>

    <form action="/erp/UpdateCartServlet" method="post">
        <table border="1">
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
                <th>Action</th>
            </tr>
            <% 
            double total = 0;
            if (cart != null && !cart.isEmpty()) {
                for (int i = 0; i < cart.size(); i++) {
                    CartItem item = cart.get(i);
                    total += item.getSubtotal();
            %>
            <tr>
                <td><%= item.product.name %></td>
                <td>$ <%= String.format("%.2f", item.product.price) %></td>
                <td>
                    <input type="number" name="quantity_<%= i %>" value="<%= item.quantity %>" min="1">
                    <input type="hidden" name="id_<%= i %>" value="<%= item.product.id %>">
                </td>
                <td>$ <%= String.format("%.2f", item.getSubtotal()) %></td>
                <td>
                    <input type="hidden" name="index_<%= i %>" value="<%= i %>">
                    <button type="submit" formaction="/erp/RemoveFromCartServlet">Remove</button>
                </td>
            </tr>
            <% 
                }
            } else { 
            %>
            <tr><td colspan="5">Your cart is empty.</td></tr>
            <% } %>
        </table>

        <br>
        <button type="submit">Update Quantities</button>
    </form>

    <form action="CheckoutServlet" method="post">
        <button type="submit">Checkout</button>
    </form>

    <br><a href="ProductListServlet">Continue Shopping</a>
</body>
</html>
