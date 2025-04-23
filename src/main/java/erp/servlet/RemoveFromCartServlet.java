package erp.servlet;

import erp.model.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int index = Integer.parseInt(request.getParameter("index"));
        HttpSession session = request.getSession(false);
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null && index >= 0 && index < cart.size()) {
            cart.remove(index);
            session.setAttribute("cart", cart);
        }

        response.sendRedirect("viewCart.jsp");
    }
}
