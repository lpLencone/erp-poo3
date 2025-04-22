package erp.servlet;

import erp.dao.UserDAO;
import erp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CadastrarUsuarioServlet")
public class CadastrarFuncionarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String roleIdStr = request.getParameter("role_id");

        if (nome == null || email == null || senha == null || roleIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Campos obrigatórios faltando.");
            return;
        }

        int roleId = Integer.parseInt(roleIdStr);

        UserDAO userDAO = new UserDAO();

        try {
            // Verifica se o email já está em uso
            if (userDAO.isEmailInUse(email)) {
                // Se o email já estiver em uso, passa a mensagem de erro para a página
                request.setAttribute("errorMessage", "Erro: O email já está em uso. Por favor, escolha outro.");
                // Redireciona de volta para a página de cadastro de usuário
                request.getRequestDispatcher("/cadastroUsuario.jsp").forward(request, response);
                return;
            }

            // Usar o UserDAO para cadastrar o usuário
            boolean sucesso = userDAO.insertUser(new User(nome, email, senha, roleId));

            if (sucesso) {
                response.sendRedirect("painel.jsp");
            } else {
                request.setAttribute("errorMessage", "Erro ao cadastrar usuário.");
                request.getRequestDispatcher("/cadastroUsuario.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            // Captura qualquer exceção SQL e mostra a mensagem de erro
            request.setAttribute("errorMessage", "Erro ao cadastrar usuário: " + e.getMessage());
            request.getRequestDispatcher("/cadastroUsuario.jsp").forward(request, response);
        }
    }
}
