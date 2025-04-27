package erp.dao;

import erp.model.InvoiceItem;
import erp.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class InvoiceItemDAO {

    public void insertInvoiceItem(InvoiceItem item) throws SQLException {
        String insertItemSQL = "INSERT INTO invoice_items (invoice_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertItemSQL)) {
            stmt.setInt(1, item.id);
            stmt.setInt(2, item.productId);
            stmt.setInt(3, item.quantity);
            stmt.executeUpdate();
        }
    }
    
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(int invoiceId) throws SQLException {
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        String sql = "SELECT id, product_id, quantity FROM invoice_items WHERE invoice_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, invoiceId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int productId = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");
                    invoiceItems.add(new InvoiceItem(id, productId, quantity));
                }
            }
        }
        return invoiceItems;
    }
}
