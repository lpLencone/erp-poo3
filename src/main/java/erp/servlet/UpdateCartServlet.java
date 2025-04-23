package erp.servlet;

import erp.model.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        String action = request.getParameter("action");
        String indexParam = request.getParameter("index");

        // Message builder to store user messages
        StringBuilder messages = new StringBuilder();

        if (cart != null && action != null && indexParam != null) {
            try {
                int index = Integer.parseInt(indexParam);
                if (index >= 0 && index < cart.size()) {
                    CartItem item = cart.get(index);
                    if ("increase".equals(action)) {
                        if (item.quantity < item.product.stock) {
                            item.quantity += 1;
                        } else {
                            messages.append("Estoque insuficiente para o produto: ")
                                    .append(item.product.name)
                                    .append("<br>");
                        }
                    } else if ("decrease".equals(action)) {
                        item.quantity = Math.max(1, item.quantity - 1); // Ensure quantity is at least 1
                    }
                }
            } catch (NumberFormatException ignored) {
                // Ignore invalid input
            }

            session.setAttribute("cart", cart);

            // Store the message in session if it exists
            if (messages.length() > 0) {
                session.setAttribute("cartMessage", messages.toString());
            } else {
                session.removeAttribute("cartMessage");
            }
        }

        response.sendRedirect("/erp/viewCart.jsp");
    }
}
