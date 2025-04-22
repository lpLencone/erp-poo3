package erp.servlet;

import erp.dao.RoleDAO;
import erp.dao.UserDAO;
import erp.model.User;
import erp.util.LogUtil; // 👈 importa o utilitário
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
        String senha = request.getParameter("password");

        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            response.getWriter().println("Por favor, preencha todos os campos.");
            return;
        }

        try {
            User user = userDAO.authenticateUser(email, senha);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userRole", roleDAO.getRoleById(user.getRoleId()).getName());
                session.setAttribute("userId", user.getId());

                // 👇 Faz o log de login
                String ip = request.getRemoteAddr();
                String userAgent = request.getHeader("User-Agent");
                LogUtil.logActionToDatabase(user.getId(), "Realizou login", ip, userAgent);

                response.sendRedirect("painel.jsp");
            } else {
                response.getWriter().println("Email ou senha inválidos!");
            }
        } catch (Exception e) {
            response.getWriter().println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
