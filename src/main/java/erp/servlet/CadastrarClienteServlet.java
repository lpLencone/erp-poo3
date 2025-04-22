package erp.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import erp.dao.RoleDAO;

@WebServlet("/CadastrarClienteServlet")
public class CadastrarClienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final String url = "jdbc:postgresql://localhost/erp";
    private final String user = "postgres";
    private final String password = "admin";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            RoleDAO roleDAO = new RoleDAO();
            int roleIdCliente;
			try {
				roleIdCliente = roleDAO.getRoleIdByName("Cliente");
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

            String sql = "INSERT INTO users (name, email, password, role_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setInt(4, roleIdCliente);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                response.sendRedirect("login.jsp"); // Redireciona para login após cadastro
            } else {
                response.getWriter().println("Erro ao cadastrar cliente.");
            }

            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            response.getWriter().println("Erro: " + e.getMessage());
        }
    }
}
