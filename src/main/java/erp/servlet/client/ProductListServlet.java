package erp.servlet.client;

import erp.dao.ProductDAO;
import erp.model.Product;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/client/ProductListServlet")
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
            List<Product> products = productDAO.getAllProducts();

            // Ordenar: produtos com estoque > 0 primeiro, depois os com estoque 0
            products.sort(Comparator.comparingInt(p -> (p.stock == 0 ? 1 : 0)));

            request.setAttribute("products", products);
            request.getRequestDispatcher("productList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar os produtos.");
        }
    }
}
