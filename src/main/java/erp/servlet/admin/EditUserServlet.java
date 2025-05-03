package erp.servlet.admin;

import erp.dao.RoleDAO;
import erp.dao.UserDAO;
import erp.model.User;
import erp.model.Role;
import erp.util.DatabaseConnection;
import erp.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/admin/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        int userId = (int) session.getAttribute("userId");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String newRoleName = request.getParameter("role");

            // Buscar roleId usando RoleDAO
            RoleDAO roleDAO = new RoleDAO();
            int roleId = roleDAO.getRoleIdByName(newRoleName);

            if (roleId == -1) {
                session.setAttribute("message", "Erro: Tipo de usuário inválido.");
                response.sendRedirect("userManagement.jsp");
                return;
            }

            // Atualizar o usuário usando UserDAO
            UserDAO userDAO = new UserDAO();
            User updatedUser = new User(id, name, email, null, roleId);
            boolean success = userDAO.updateUser(updatedUser);

            if (success) {
            	LogUtil.logActionToDatabase(userId, "Excluiu atualizou usuário de id " + id + "como nome " + name + ", email: " + email + ", roleId: " + roleId, ip, userAgent);
                session.setAttribute("message", "Usuário atualizado com sucesso.");
            } else {
            	LogUtil.logActionToDatabase(userId, "Não conseguiu atualizar usuário de id" + id, ip, userAgent);
                session.setAttribute("message", "Erro ao atualizar o usuário.");
            }

            response.sendRedirect("userManagement.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Erro no processamento: " + e.getMessage());
            response.sendRedirect("userManagement.jsp");
        }
    }
}
