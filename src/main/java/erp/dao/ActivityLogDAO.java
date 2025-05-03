package erp.dao;

import erp.model.ActivityLog;
import erp.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogDAO {

    public boolean insertLog(ActivityLog log) {
        String sql = "INSERT INTO activity_logs (action, timestamp, ip_address, user_agent) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, log.action);
            stmt.setTimestamp(2, log.timestamp);
            stmt.setString(3, log.ipAddress);
            stmt.setString(4, log.userAgent);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ActivityLog> getAllLogs() {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT id, action, timestamp, ip_address, user_agent FROM activity_logs ORDER BY timestamp DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String action = rs.getString("action");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                String ipAddress = rs.getString("ip_address");
                String userAgent = rs.getString("user_agent");
                int id = rs.getInt("id");

                ActivityLog log = new ActivityLog(id, action, timestamp, ipAddress, userAgent);
                logs.add(log);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }
}
