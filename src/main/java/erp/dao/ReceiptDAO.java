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
}
