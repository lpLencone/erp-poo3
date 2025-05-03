package erp.dao;

import erp.model.Invoice;
import erp.util.DatabaseConnection;

import java.sql.Date;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
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
    
    public List<Invoice> getInvoicesByUserId(int userId) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices WHERE user_id = ? ORDER BY date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice(
                    rs.getInt("user_id"),
                    rs.getDouble("total"),
                    rs.getString("date")
                );
                invoice.id = rs.getInt("id");
                invoices.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

}
