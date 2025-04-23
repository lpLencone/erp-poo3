package erp.servlet;

import erp.model.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            // TODO: Save to the database as an order (orders + order_items), and update stock if needed.

            session.removeAttribute("cart");
            request.setAttribute("message", "Compra finalizada com sucesso!");
        } else {
            request.setAttribute("message", "Carrinho está vazio.");
        }

        request.getRequestDispatcher("purchaseConfirmation.jsp").forward(request, response);
    }
}
