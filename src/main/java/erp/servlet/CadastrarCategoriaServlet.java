package erp.servlet;

import erp.dao.CategoryDAO;
import erp.model.Category;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/CadastrarCategoriaServlet")
public class CadastrarCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO(); // Inicializa o CategoryDAO
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Cria uma instância do modelo Category
            Category category = new Category();
            category.setName(nome);
            category.setDescription(descricao);

            // Chama o método do CategoryDAO para cadastrar a categoria
            boolean sucesso = categoryDAO.insertCategory(category);

            if (sucesso) {
                out.println("<h3>Categoria cadastrada com sucesso!</h3>");
            } else {
                out.println("<h3>Erro ao cadastrar categoria.</h3>");
            }

        } catch (Exception e) {
            out.println("<h3>Erro: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }

        out.println("<br><a href=\"/erp/cadastroCategoria.jsp\">Voltar</a>");
    }
}
