package erp.dao;

import erp.model.Invoice;
import erp.util.DatabaseConnection;

import java.sql.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class InvoiceDAO {

    public int insertInvoice(Invoice invoice) throws SQLException {
        String insertInvoiceSQL = "INSERT INTO invoices (total, user_id, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertInvoiceSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, invoice.total);
            stmt.setInt(2, invoice.userId);
            stmt.setDate(3, Date.valueOf(invoice.date));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);  // Retorna o ID gerado da fatura
            }
        }
    }
}
