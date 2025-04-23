package erp.servlet.admin;

import erp.dao.CategoryDAO;
import erp.model.Category;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/admin/RegisterCategoryServlet")
public class RegisterCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");

        try {
            String message;

            if (categoryDAO.categoryExists(name)) {
                message = "Erro: A categoria já existe!";
            } else {
                Category category = new Category(name);
                boolean success = categoryDAO.insertCategory(category);

                if (success) {
                    message = "Categoria cadastrada com sucesso!";
                } else {
                    message = "Erro ao cadastrar categoria.";
                }
            }

            // Envia a mensagem para a página JSP
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("registerCategory.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("message", "Erro: " + e.getMessage());
            request.getRequestDispatcher("registerCategory.jsp").forward(request, response);
        }
    }
}

