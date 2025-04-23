package erp.servlet;

import erp.dao.ProductDAO;
import erp.model.Product;
import erp.model.CartItem; // Certifique-se de ter renomeado ItemCarrinho para CartItem

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        ProductDAO dao = new ProductDAO();
        Product product = dao.getProductById(productId);

        if (product != null) {
            HttpSession session = request.getSession(false);
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

            if (cart == null) {
                cart = new ArrayList<>();
            }

            boolean found = false;
            for (CartItem item : cart) {
                if (item.product.id == product.id) {
                    item.quantity++;
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(product, 1));
            }

            session.setAttribute("cart", cart);
        }

        response.sendRedirect("/erp/ProductListServlet");
    }
}
