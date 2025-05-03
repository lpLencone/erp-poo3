<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, erp.dao.*, erp.model.*" %>
<%
    String username = (String) session.getAttribute("username");
    Integer userId = (Integer) session.getAttribute("userId");

    if (username == null || userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    InvoiceDAO invoiceDAO = new InvoiceDAO();
    InvoiceItemDAO itemDAO = new InvoiceItemDAO();
    ReceiptDAO receiptDAO = new ReceiptDAO();
    ProductDAO productDAO = new ProductDAO();

    List<Invoice> invoices = invoiceDAO.getInvoicesByUserId(userId);
%>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Histórico de Compras</title>
    <style>
        body {
            font-family: sans-serif;
            padding: 20px;
        }
        .invoice {
            border: 1px solid #aaa;
            padding: 15px;
            margin-bottom: 20px;
        }
        .invoice h3 {
            margin-top: 0;
        }
        .items, .receipt {
            margin-top: 10px;
            margin-left: 20px;
        }
    </style>
</head>
<body>
    <h1>Histórico de Compras</h1>
    <p>Olá, <%= username %>!</p>

    <%
        if (invoices.isEmpty()) {
    %>
        <p>Você ainda não fez nenhuma compra.</p>
    <%
        } else {
            for (Invoice invoice : invoices) {
                List<InvoiceItem> items = itemDAO.getItemsByInvoiceId(invoice.id);
                Receipt receipt = receiptDAO.getReceiptByInvoiceId(invoice.id);
    %>
    <div class="invoice">
        <h3>Fatura #<%= invoice.id %> — <%= invoice.date %></h3>
        <p><strong>Total:</strong> R$ <%= String.format("%.2f", invoice.total) %></p>

        <div class="items">
            <h4>Itens:</h4>
            <ul>
                <%
                    for (InvoiceItem item : items) {
                        Product p = productDAO.getProductById(item.productId);
                %>
                    <li><%= p.name %> — Quantidade: <%= item.quantity %></li>
                <%
                    }
                %>
            </ul>
        </div>

        <div class="receipt">
            <h4>Recibo:</h4>
            <%
                if (receipt != null) {
            %>
                <p>ID: <%= receipt.id %></p>
                <p>Data: <%= receipt.date %></p>
                <p>Total Pago: R$ <%= String.format("%.2f", receipt.totalPaid) %></p>
            <%
                } else {
            %>
                <p style="color:red;">Recibo ainda não disponível.</p>
            <%
                }
            %>
        </div>
    </div>
    <%
            }
        }
    %>

    <p><a href="productList.jsp">Voltar ao painel</a></p>
</body>
</html>
