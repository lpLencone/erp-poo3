package erp.servlet;

import erp.dao.UserDAO;
import erp.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserListServlet")
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
        int roleId = roleToId(role);

        List<User> users = userDAO.findUsersByRoleId(roleId);

        request.setAttribute("users", users);
        request.setAttribute("filtro", role);
        request.getRequestDispatcher("userManagement.jsp").forward(request, response);
    }

    private int roleToId(String role) {
        switch (role) {
            case "Administrador": return 1;
            case "Gerente": return 2;
            case "Funcionario": return 3;
            case "Cliente": return 4;
            default: return -1;
        }
    }
}
