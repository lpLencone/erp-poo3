package erp.servlet.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import erp.dao.RoleDAO;
import erp.dao.UserDAO;
import erp.model.User;
import erp.util.LogUtil;

@WebServlet("/client/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        try {
            RoleDAO roleDAO = new RoleDAO();
            int roleIdCliente = roleDAO.getRoleIdByName("Cliente");

            UserDAO userDAO = new UserDAO();

            // Verifica se o e-mail já está em uso
            if (userDAO.isEmailInUse(email)) {
            	LogUtil.logActionToDatabase(0, "Novo usuário tentou cadastrar email já usado.", ip, userAgent);
                request.setAttribute("errorMessage", "O e-mail informado já está em uso.");
                request.getRequestDispatcher("/client/register.jsp").forward(request, response);
                return;
            }

            User user = new User();
            user.name = name;
            user.email = email;
            user.password = password;
            user.roleId = roleIdCliente;

            boolean sucesso = userDAO.insertUser(user);

            if (sucesso) {
            	LogUtil.logActionToDatabase(0, "Novo usuário criado: " + user.email, ip, userAgent);
                response.sendRedirect("/erp/login.jsp");
            } else {
            	LogUtil.logActionToDatabase(0, "Cliente não pode ser cadastrado: " + user.email, ip, userAgent);
                request.setAttribute("errorMessage", "Erro ao cadastrar cliente. Tente novamente.");
                request.getRequestDispatcher("/client/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erro: " + e.getMessage());
            request.getRequestDispatcher("/client/register.jsp").forward(request, response);
        }
    }
}
