package erp.servlet.admin;

import erp.dao.UserDAO;
import erp.exception.UserNotFoundException;
import erp.util.LogUtil;

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

        int userId = Integer.parseInt(request.getParameter("userId"));
        String userFilter = request.getParameter("userFilter");
        
        HttpSession session = request.getSession(false);
        int loggedId = (int) session.getAttribute("userId");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        try {
            userDAO.deleteUserById(userId);
            LogUtil.logActionToDatabase(userId, "Excluiu usuário de id " + userId, ip, userAgent);
            response.sendRedirect("UserListServlet?role=" + userFilter);
        } catch (UserNotFoundException e) {
            // Redireciona para a página de gestão de usuários com a mensagem de erro
        	LogUtil.logActionToDatabase(userId, "Excluiu não conseguiu excluir usuário de id " + userId, ip, userAgent);
            response.sendRedirect("userManagement.jsp?error=" + e.getMessage());
        } catch (SQLException e) {
            // Trate erros de SQL genéricos (como falha na conexão com o banco)
            response.sendRedirect("userManagement.jsp?error=Erro de banco de dados: " + e.getMessage());
        }
    }
}
