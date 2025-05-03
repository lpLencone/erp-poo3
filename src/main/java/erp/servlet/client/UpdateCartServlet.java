package erp.servlet.client;

import erp.dao.ProductDAO;
import erp.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/client/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        String action = request.getParameter("action");
        String productIdParam = request.getParameter("productId");

        // 
        StringBuilder messages = new StringBuilder();

        if (cart != null && action != null && productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                Product product = productDAO.getProductById(productId);

                if (product != null && cart.containsKey(productId)) {
                    int quantity = cart.get(productId);

                    switch (action) {
                        case "increase":
                            if (quantity < product.stock) {
                                cart.put(productId, quantity + 1);
                            } else {
                                messages.append("Estoque insuficiente para o produto: ")
                                        .append(product.name)
                                        .append("<br>");
                            }
                            break;
                        case "decrease":
                            // Garante que a quantidade nunca seja menor que 1
                            cart.put(productId, Math.max(1, quantity - 1));
                            break;
                    }
                }
            } catch (NumberFormatException ignored) {
            }

            session.setAttribute("cart", cart);

            if (messages.length() > 0) {
                session.setAttribute("cartMessage", messages.toString());
            } else {
                session.removeAttribute("cartMessage");
            }
        }

        response.sendRedirect("viewCart.jsp");
    }
}
