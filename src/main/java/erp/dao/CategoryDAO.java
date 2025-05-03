package erp.dao;

import erp.model.Category;
import erp.util.DatabaseConnection; // Importa a classe DatabaseConnection
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // Método para recuperar todas as categorias
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
    
    public boolean categoryExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM categories WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // Método para inserir uma nova categoria
    public boolean insertCategory(Category category) throws SQLException {
        if (categoryExists(category.name)) {
            return false; // Categoria já existe
        }

        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.name);
            return stmt.executeUpdate() > 0;
        }
    }

    // Método para atualizar uma categoria existente
    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.name);
            stmt.setInt(2, category.id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Método para deletar uma categoria e lançar exceção em caso de erro
    public boolean deleteCategoryById(int categoryId) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }


	public Category getCategoryById(int id) {
		String sql = "SELECT categories WHERE id = ?";
		Category category = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, id);
        	ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
            	category = new Category(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
	}
}
