package erp.servlet.admin;

import erp.dao.UserDAO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userRole = (String) request.getSession().getAttribute("userRole");
        if (userRole == null || (!userRole.equals("Administrador") && !userRole.equals("Gerente"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Acesso não autorizado.");
            return;
        }

        int userId = Integer.parseInt(request.getParameter("userId"));
        String userType = request.getParameter("userType");

        // Verifica permissões
        if (userRole.equals("Gerente") && (userType.equals("Administrador") || userType.equals("Gerente"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("Gerentes não podem deletar administradores ou outros gerentes.");
            return;
        }

        boolean deleted = userDAO.deleteUserById(userId);

        if (deleted) {
            response.sendRedirect("UserListServlet?role=" + userType);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h3>Erro ao deletar usuário.</h3>");
        }
    }
}
