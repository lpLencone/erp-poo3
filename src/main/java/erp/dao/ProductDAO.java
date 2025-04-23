package erp.dao;

import erp.model.Product;
import erp.util.DatabaseConnection;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ProductDAO {

    public boolean createProduct(Product product, int supplierId) {
        String sql = "INSERT INTO products (name, description, price, stock, category_id) VALUES (?, ?, ?, ?, ?)";
        String supplierLinkSQL = "INSERT INTO supplier_products (supplier_id, product_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, product.name);
            stmt.setString(2, product.description);
            stmt.setDouble(3, product.price);
            stmt.setInt(4, product.stock);
            stmt.setInt(5, product.categoryId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int productId = generatedKeys.getInt(1);

                        try (PreparedStatement supplierStmt = conn.prepareStatement(supplierLinkSQL)) {
                            supplierStmt.setInt(1, supplierId);
                            supplierStmt.setInt(2, productId);
                            supplierStmt.executeUpdate();
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Product getProductById(int id) {
        String sql = "SELECT id, name, description, price, stock, category_id FROM products WHERE id = ?";
        Product p = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
        	stmt.setInt(1, id);
        	ResultSet rs = stmt.executeQuery();
        	if (rs.next()) {
        		p = new Product(
        				rs.getInt("id"), 
        				rs.getString("name"), 
        				rs.getString("description"), 
        				rs.getDouble("price"),  
        				rs.getInt("category_id"),
        				rs.getInt("stock")
				);        		
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

	public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
		String sql = "SELECT id, name, description, price, stock, category_id FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product p = new Product(
                		rs.getInt("id"), 
                		rs.getString("name"), 
                		rs.getString("description"), 
                		rs.getDouble("price"),  
                		rs.getInt("category_id"),
                		rs.getInt("stock")
                );
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
	}
}
