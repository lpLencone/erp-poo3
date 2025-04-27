package erp.servlet.client;

import erp.dao.ProductDAO;
import erp.dao.InvoiceDAO;
import erp.dao.InvoiceItemDAO;
import erp.dao.ReceiptDAO;

import erp.model.Invoice;
import erp.model.InvoiceItem;
import erp.model.Product;
import erp.model.Receipt;

import erp.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.Date;

@WebServlet("/client/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("viewCart.jsp");
            return;
        }

        ProductDAO productDAO = new ProductDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();
        ReceiptDAO receiptDAO = new ReceiptDAO();

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            double total = 0;
            Date sqlDate = new Date(System.currentTimeMillis());

            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                Product product = productDAO.getProductById(productId);
                if (product == null || quantity > product.stock) {
                    session.setAttribute("cartMessage", "Produto sem estoque suficiente: " + 
                                            (product != null ? product.name : "ID " + productId));
                    response.sendRedirect("viewCart.jsp");
                    return;
                }

                total += product.price * quantity;
            }

            int userId = (int) session.getAttribute("userId");
            int invoiceId = invoiceDAO.insertInvoice(new Invoice(userId, total, sqlDate.toString()));

            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                invoiceItemDAO.insertInvoiceItem(new InvoiceItem(invoiceId, productId, quantity));

                String updateStockSQL = "UPDATE products SET stock = stock - ? WHERE id = ?";
                try (PreparedStatement stockStmt = conn.prepareStatement(updateStockSQL)) {
                    stockStmt.setInt(1, quantity);
                    stockStmt.setInt(2, productId);
                    stockStmt.executeUpdate();
                }
            }

            int receiptId = receiptDAO.insertReceipt(new Receipt(invoiceId, invoiceId, total, sqlDate.toString()));

            conn.commit();

            // Carrega os itens da fatura se quiser exibir na view, mas não atribui ao modelo Invoice
            List<InvoiceItem> invoiceItems = invoiceItemDAO.getInvoiceItemsByInvoiceId(invoiceId);

            Invoice invoice = new Invoice(userId, total, sqlDate.toString());
            Receipt receipt = new Receipt(receiptId, invoiceId, total, sqlDate.toString());

            session.setAttribute("invoice", invoice);
            session.setAttribute("receipt", receipt);
            session.setAttribute("invoiceItems", invoiceItems);
            session.removeAttribute("cart");

            response.sendRedirect("checkoutSuccess.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erro ao finalizar a compra. Tente novamente.");
            request.getRequestDispatcher("viewCart.jsp").forward(request, response);
        }
    }
}
