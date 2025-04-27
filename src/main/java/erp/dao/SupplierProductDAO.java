package erp.dao;

import erp.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierProductDAO {

    public boolean insertSupplierProduct(int supplierId, int productId) {
        String sql = "INSERT INTO supplier_products (supplier_id, product_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);
            stmt.setInt(2, productId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
