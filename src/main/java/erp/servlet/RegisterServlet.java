package erp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import erp.dao.RoleDAO;
import erp.dao.UserDAO;
import erp.model.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Obtém o ID do papel "Cliente"
            RoleDAO roleDAO = new RoleDAO();
            int roleIdCliente = roleDAO.getRoleIdByName("Cliente");

            // Cria um novo usuário com o papel de cliente
            User user = new User();
            user.name = name;
            user.email = email;
            user.password = password;
            user.roleId = roleIdCliente;

            // Insere o usuário no banco usando UserDAO
            UserDAO userDAO = new UserDAO();
            boolean sucesso = userDAO.insertUser(user);

            if (sucesso) {
                response.sendRedirect("login.jsp");
            } else {
                response.getWriter().println("Erro ao cadastrar cliente.");
            }

        } catch (Exception e) {
            response.getWriter().println("Erro: " + e.getMessage());
        }
    }
}
