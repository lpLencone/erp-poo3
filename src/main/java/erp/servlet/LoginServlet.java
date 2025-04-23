package erp.servlet;

import erp.dao.RoleDAO;
import erp.dao.UserDAO;
import erp.model.User;
import erp.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        roleDAO = new RoleDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
        	response.sendRedirect("login.jsp?error=1");
            return;
        }

        try {
            User user = userDAO.authenticateUser(email, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userRole", roleDAO.getRoleById(user.roleId).name);
                session.setAttribute("userId", user.id);

                String ip = request.getRemoteAddr();
                String userAgent = request.getHeader("User-Agent");
                LogUtil.logActionToDatabase(user.id, "Realizou login", ip, userAgent);

                response.sendRedirect("painel.jsp");
            } else {
            	response.sendRedirect("login.jsp?error=1");
            }
        } catch (Exception e) {
            response.getWriter().println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
