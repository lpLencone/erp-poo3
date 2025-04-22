package erp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class LogUtil {

    public static void logActionToDatabase(int userId, String action, String ip, String userAgent) {
        String sql = "INSERT INTO activity_logs (user_id, action, ip_address, user_agent, timestamp) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, action);
            stmt.setString(3, ip);
            stmt.setString(4, userAgent);
            stmt.setObject(5, LocalDateTime.now());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Erro ao registrar log de atividade:");
            e.printStackTrace();
        }
    }
}
