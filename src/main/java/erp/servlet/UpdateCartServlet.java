package erp.servlet;

import erp.model.CartItem; // Certifique-se de ter renomeado ItemCarrinho para CartItem

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (int i = 0; i < cart.size(); i++) {
                String param = request.getParameter("quantity_" + i);
                if (param != null) {
                    try {
                        int quantity = Integer.parseInt(param);
                        cart.get(i).quantity = Math.max(1, quantity); // Avoid zero or negative values
                    } catch (NumberFormatException ignored) {}
                }
            }

            session.setAttribute("cart", cart);
        }

        response.sendRedirect("viewCart.jsp");
    }
}
