package erp.servlet.client;

import erp.dao.ProductDAO;
import erp.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/client/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            response.sendRedirect("ProductListServlet");
            return;
        }

        HttpSession session = request.getSession();
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        int currentQty = cart.getOrDefault(productId, 0);
        int newQty = Math.min(currentQty + quantity, product.stock);  // Limita a quantidade ao estoque máximo

        cart.put(productId, newQty);  // Atualiza a quantidade no carrinho

        session.setAttribute("cart", cart);  // Salva o carrinho na sessão
        session.setAttribute("cartMessage", "A quantidade de '" + product.name + "' foi atualizada.");  // Mensagem de feedback
        response.sendRedirect("ProductListServlet");  // Redireciona de volta para a lista de produtos
    }
}
