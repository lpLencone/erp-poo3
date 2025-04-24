package erp.servlet.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/client/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParam = request.getParameter("productId");
        HttpSession session = request.getSession(false);
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart != null && productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                cart.remove(productId);
                session.setAttribute("cart", cart);
            } catch (NumberFormatException ignored) {
            }
        }

        response.sendRedirect("viewCart.jsp");
    }
}
