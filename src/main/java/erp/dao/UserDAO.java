package erp.dao;

import erp.exception.UserNotFoundException;
import erp.model.User;
import erp.util.DatabaseConnection; // Importa a classe DatabaseConnection
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    // Método para inserir um novo usuário
	public boolean insertUser(User u) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); // Usa a classe para obter a conexão
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.name);
            stmt.setString(2, u.email);
            stmt.setString(3, u.password);
            stmt.setInt(4, u.roleId);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
    }
	
	public boolean isEmailInUse(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	// Retorna true se o count for maior que zero (email já estiver em uso)
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // Método para obter um usuário pelo email e senha
    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.name = rs.getString("name");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
                user.roleId = rs.getInt("role_id");
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<User> findUsersByRoleName(String roleName) {
        List<User> users = new ArrayList<>();
        String sql =
            "SELECT u.id, u.name, u.email, u.password, u.role_id " +
            "FROM users u " +
            "JOIN roles r ON u.role_id = r.id " +
            "WHERE r.name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.name = rs.getString("name");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
                user.roleId = rs.getInt("role_id");
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    // Método para obter todos os usuários
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User u = new User();
                u.id = rs.getInt("id");
                u.name = rs.getString("name");
                u.email = rs.getString("email");
                u.password = rs.getString("password");
                u.roleId = rs.getInt("role_id");
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public void deleteUserById(int id) throws SQLException, UserNotFoundException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            // Caso não tenha encontrado o usuário para deletar
            if (rowsAffected == 0) {
                throw new UserNotFoundException("Nenhum usuário encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao tentar deletar o usuário: " + e.getMessage(), e);
        }
    }
    // Método para autenticar um usuário com base no email e senha
    public User authenticateUser(String email, String password) {
        User user = null;
        String sql = "SELECT id, name, email, password, role_id FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.id = rs.getInt("id");
                user.name = rs.getString("name");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
                user.roleId = rs.getInt("role_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
