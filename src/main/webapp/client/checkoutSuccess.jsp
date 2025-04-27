<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, erp.model.*, erp.dao.ProductDAO" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Compra Concluída</title>
</head>
<body>
    <div class="container">
        <h1>Compra Concluída com Sucesso!</h1>

        <%
            Invoice invoice = (Invoice) session.getAttribute("invoice");
            Receipt receipt = (Receipt) session.getAttribute("receipt");
            List<InvoiceItem> invoiceItems = (List<InvoiceItem>) session.getAttribute("invoiceItems");

            if (invoice != null && receipt != null && invoiceItems != null) {
                ProductDAO productDAO = new ProductDAO();
        %>

        <h2>Fatura</h2>
        <p><strong>Emitido para:</strong> <%= session.getAttribute("username") %></p>
        <p><strong>Data:</strong> <%= invoice.date %></p>
        <p><strong>Total:</strong> R$ <%= String.format("%.2f", invoice.total) %></p>

        <h3>Itens da Fatura</h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Produto</th>
                    <th>Preço Unitário</th>
                    <th>Quantidade</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (InvoiceItem item : invoiceItems) {
                        Product product = productDAO.getProductById(item.productId);
                        double totalItem = product.price * item.quantity;
                %>
                    <tr>
                        <td>R$ <%= String.format("%.2f", product.price) %></td>
                        <td><%= item.quantity %></td>
                        <td>R$ <%= String.format("%.2f", totalItem) %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <h2>Recibo</h2>
        <p><strong>Total Pago:</strong> R$ <%= String.format("%.2f", receipt.totalPaid) %></p>
        <p><strong>Data:</strong> <%= receipt.date %></p>

        <%
            } else {
        %>
            <p>Informações da compra não disponíveis.</p>
        <%
            }
        %>

        <br>
        <a href="ProductListServlet">Voltar à Lista de Produtos</a>
    </div>
</body>
</html>
