package erp.servlet.admin;

import erp.dao.UserDAO;
import erp.model.User;
import erp.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/RegisterEmployeeServlet")
public class RegisterEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleIdStr = request.getParameter("role_id");

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        int id = 0;

        if (name == null || email == null || password == null || roleIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Campos obrigatórios faltando.");
            return;
        }

        int roleId = Integer.parseInt(roleIdStr);

        UserDAO userDAO = new UserDAO();

        try {
            if (userDAO.isEmailInUse(email)) {
                request.setAttribute("errorMessage", "Erro: O email já está em uso. Por favor, escolha outro.");
                LogUtil.logActionToDatabase(userId, "Tentou cadastrar o email ja existente " + email, ip, userAgent);
                request.getRequestDispatcher("admin/registerEmployee.jsp").forward(request, response);
                return;
            }

            boolean success = userDAO.insertUser(new User(name, email, password, roleId));

            if (success) {
                LogUtil.logActionToDatabase(userId, "Cadastrou o usuário " + name, ip, userAgent);
                response.sendRedirect("/erp/employee/adminPanel.jsp");
            } else {
                request.setAttribute("errorMessage", "Erro ao cadastrar usuário.");
                LogUtil.logActionToDatabase(userId, "Não conseguiu cadastrar o usuário " + name, ip, userAgent);
                request.getRequestDispatcher("registerEmployee.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao cadastrar usuário: " + e.getMessage());
            request.getRequestDispatcher("registerEmployee.jsp").forward(request, response);
        }
    }
}
