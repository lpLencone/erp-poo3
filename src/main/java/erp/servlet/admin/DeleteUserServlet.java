package erp.servlet.admin;

import erp.dao.UserDAO;
import erp.exception.UserNotFoundException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

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
            // TODO: redirecionar para unauthorized.jsp
            // /erp/unauthorized.jsp
            // /admin/deleteuser...
        }

        int userId = Integer.parseInt(request.getParameter("userId"));
        String userFilter = request.getParameter("userFilter");

        // Verifica permissões
        if (userRole.equals("Gerente") && (userFilter.equals("Administrador") || userFilter.equals("Gerente"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("Gerentes não podem deletar administradores ou outros gerentes.");
            return;
        }

        try {
            userDAO.deleteUserById(userId);
            response.sendRedirect("UserListServlet?role=" + userFilter);
        } catch (UserNotFoundException e) {
            // Redireciona para a página de gestão de usuários com a mensagem de erro
            response.sendRedirect("userManagement.jsp?error=" + e.getMessage());
        } catch (SQLException e) {
            // Trate erros de SQL genéricos (como falha na conexão com o banco)
            response.sendRedirect("userManagement.jsp?error=Erro de banco de dados: " + e.getMessage());
        }
    }
}
