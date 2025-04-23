package erp.servlet;

import erp.dao.UserDAO;
import erp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RegisterEmployeeServlet")
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

        if (name == null || email == null || password == null || roleIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Campos obrigatórios faltando.");
            return;
        }

        int roleId = Integer.parseInt(roleIdStr);

        UserDAO userDAO = new UserDAO();

        try {
            if (userDAO.isEmailInUse(email)) {
                request.setAttribute("errorMessage", "Erro: O email já está em uso. Por favor, escolha outro.");
                request.getRequestDispatcher("admin/registerEmployee.jsp").forward(request, response);
                return;
            }

            boolean success = userDAO.insertUser(new User(name, email, password, roleId));

            if (success) {
                response.sendRedirect("painel.jsp");
            } else {
                request.setAttribute("errorMessage", "Erro ao cadastrar usuário.");
                request.getRequestDispatcher("admin/registerEmployee.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao cadastrar usuário: " + e.getMessage());
            request.getRequestDispatcher("admin/registerEmployee.jsp").forward(request, response);
        }
    }
}
