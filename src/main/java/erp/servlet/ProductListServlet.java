package erp.servlet;

import erp.dao.ProductDAO;
import erp.model.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ProductListServlet")
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Recupera a lista de produtos do banco de dados
            List<Product> products = productDAO.getAllProducts();

            // Define a lista de produtos como atributo na requisição
            request.setAttribute("products", products);

            // Redireciona para o JSP que exibirá os produtos
            request.getRequestDispatcher("/productList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar os produtos.");
        }
    }
}
