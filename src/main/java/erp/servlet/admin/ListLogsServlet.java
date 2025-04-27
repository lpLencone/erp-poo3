package erp.servlet.admin;

import erp.model.ActivityLog;
import erp.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/ListLogsServlet")
public class ListLogsServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT action, timestamp, ip_address, user_agent FROM activity_logs ORDER BY timestamp DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ActivityLog log = new ActivityLog(
                    rs.getString("action"),
                    rs.getTimestamp("timestamp"),
                    rs.getString("ip_address"),
                    rs.getString("user_agent")
                );
                logs.add(log);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erro ao buscar logs do banco de dados.");
        }

        request.setAttribute("logs", logs);
        request.getRequestDispatcher("listLogs.jsp").forward(request, response);
    }
}
