package erp.dao;

import erp.model.Receipt;
import erp.util.DatabaseConnection;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Date;
import java.sql.ResultSet;

public class ReceiptDAO {

    public int insertReceipt(Receipt receipt) throws SQLException {
        String insertReceiptSQL = "INSERT INTO receipts (invoice_id, total_paid, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertReceiptSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, receipt.invoiceId);
            stmt.setDouble(2, receipt.totalPaid);
            stmt.setDate(3, Date.valueOf(receipt.date));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);  // Retorna o ID gerado do recibo
            }
        }
    }
    
    public Receipt getReceiptByInvoiceId(int invoiceId) {
        String sql = "SELECT * FROM receipts WHERE invoice_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Receipt(
                    rs.getInt("id"),
                    rs.getInt("invoice_id"),
                    rs.getDouble("total_paid"),
                    rs.getString("date")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
