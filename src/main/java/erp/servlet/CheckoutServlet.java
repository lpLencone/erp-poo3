package erp.servlet;

import erp.model.CartItem;
import erp.model.Invoice;
import erp.model.Receipt;
import erp.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("viewCart.jsp");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Step 1: Validate stock
            for (CartItem item : cart) {
                String checkStockSql = "SELECT stock FROM products WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(checkStockSql)) {
                    stmt.setInt(1, item.product.id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            int stock = rs.getInt("stock");
                            if (item.quantity > stock) {
                                session.setAttribute("cartMessage", "Produto sem estoque suficiente: " + item.product.name);
                                response.sendRedirect("viewCart.jsp");
                                return;
                            }
                        }
                    }
                }
            }

            // Step 2: Insert into invoices
            String insertInvoiceSQL = "INSERT INTO invoices (total, issued_to, date) VALUES (?, ?, ?)";
            int invoiceId = -1;
            double total = cart.stream().mapToDouble(CartItem::getSubtotal).sum();
            String issuedTo = "Client Name"; // pode ser vindo de login
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            try (PreparedStatement stmt = conn.prepareStatement(insertInvoiceSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDouble(1, total);
                stmt.setString(2, issuedTo);
                stmt.setString(3, date);
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        invoiceId = generatedKeys.getInt(1);
                    }
                }
            }

            // Step 3: Insert items and update stock
            String insertItemSQL = "INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            String updateStockSQL = "UPDATE products SET stock = stock - ? WHERE id = ?";

            for (CartItem item : cart) {
                try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSQL)) {
                    itemStmt.setInt(1, invoiceId);
                    itemStmt.setInt(2, item.product.id);
                    itemStmt.setInt(3, item.quantity);
                    itemStmt.setDouble(4, item.product.price);
                    itemStmt.executeUpdate();
                }

                try (PreparedStatement stockStmt = conn.prepareStatement(updateStockSQL)) {
                    stockStmt.setInt(1, item.quantity);
                    stockStmt.setInt(2, item.product.id);
                    stockStmt.executeUpdate();
                }
            }

            // Step 4: Insert receipt
            String insertReceiptSQL = "INSERT INTO receipts (invoice_id, total_paid, date) VALUES (?, ?, ?)";
            int receiptId = -1;

            try (PreparedStatement stmt = conn.prepareStatement(insertReceiptSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, invoiceId);
                stmt.setDouble(2, total);
                stmt.setString(3, date);
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        receiptId = generatedKeys.getInt(1);
                    }
                }
            }

            conn.commit();

            // Step 5: Generate objects to display
            Invoice invoice = new Invoice(invoiceId, cart, total, issuedTo, date);
            Receipt receipt = new Receipt(receiptId, invoiceId, total, date);

            // Step 6: Store and redirect
            session.setAttribute("invoice", invoice);
            session.setAttribute("receipt", receipt);
            session.removeAttribute("cart");
 
            response.sendRedirect("checkoutSuccess.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erro ao finalizar a compra. Tente novamente.");
            request.getRequestDispatcher("viewCart.jsp").forward(request, response);
        }
    }
}
