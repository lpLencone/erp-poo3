package erp.dao;

import erp.model.Supplier;
import erp.util.DatabaseConnection; // Importa a classe DatabaseConnection
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    // Criar um novo fornecedor
    public boolean createSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (name, contact_info) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); // Usa a classe para obter a conexão
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.name);
            stmt.setString(2, supplier.contactInfo);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obter todos os fornecedores
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT id, name, contact_info FROM suppliers";
        
        try (Connection conn = DatabaseConnection.getConnection(); // Usa a classe para obter a conexão
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Supplier supplier = new Supplier(rs.getInt("id"), rs.getString("name"), rs.getString("contact_info"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    // Obter um fornecedor por ID
    public Supplier getSupplierById(int id) {
        Supplier supplier = null;
        String sql = "SELECT id, name, contact_info FROM suppliers WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); // Usa a classe para obter a conexão
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    supplier = new Supplier(rs.getInt("id"), rs.getString("name"), rs.getString("contact_info"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    // Atualizar um fornecedor
    public boolean updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET name = ?, contact_info = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // Usa a classe para obter a conexão
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.name);
            stmt.setString(2, supplier.contactInfo);
            stmt.setInt(3, supplier.id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deletar um fornecedor
    public boolean deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // Usa a classe para obter a conexão
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
