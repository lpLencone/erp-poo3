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

        HttpSession session = request.getSession(false);
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        // Atualizar todos os produtos enviados
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder messageBuilder = new StringBuilder();

        for (String paramName : parameterMap.keySet()) {
            if (paramName.startsWith("quantity_")) {
                int productId = Integer.parseInt(paramName.substring(9)); // depois de "quantity_"
                int quantity = Integer.parseInt(request.getParameter(paramName));

                Product product = productDAO.getProductById(productId);
                if (cart.containsKey(productId) && (product == null || product.stock == 0)) {
                    cart.remove(productId);
                    messageBuilder
                    	.append("Produto removido do carrinho (fora de estoque): ID ")
                    	.append(productId)
                    	.append(". ");
                    continue;
                }

                int newQty = Math.min(quantity, product.stock);

                if (newQty > 0 && cart.getOrDefault(productId, 0) != newQty) {
                    cart.put(productId, newQty);
                    messageBuilder.append("'" + product.name + "' atualizado para " + newQty + ". ");
                } else if (cart.containsKey(productId)) {
                    cart.remove(productId);
                    messageBuilder.append("'" + product.name + "' removido do carrinho. ");
                }
            }
        }

        session.setAttribute("cart", cart);
        session.setAttribute("cartMessage", messageBuilder.toString());
        response.sendRedirect("ProductListServlet");
    }
}
