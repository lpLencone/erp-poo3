package erp.servlet.admin;

import erp.dao.UserDAO;
import erp.model.User;
import erp.util.LogUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/UserListServlet")
public class UserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");
        List<User> users = userDAO.findUsersByRoleName(role);

        request.setAttribute("users", users);
        request.setAttribute("roleFilter", role);
        request.getRequestDispatcher("userManagement.jsp").forward(request, response);
    }
}
