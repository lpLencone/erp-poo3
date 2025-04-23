package erp.servlet;

import erp.dao.ProductDAO;
import erp.model.CartItem;
import erp.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AddToCartServlet")
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
            response.sendRedirect("/erp/ProductListServlet");
            return;
        }

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean found = false;
        for (CartItem item : cart) {
        	if (item.product.id == productId) {
        	    int newQuantity = item.quantity + quantity;
        	    if (newQuantity <= product.stock) {
        	        item.quantity = newQuantity;
        	    } else {
        	        item.quantity = product.stock;
        	        session.setAttribute("cartMessage", "A quantidade de '" + product.name + "' foi ajustada para o máximo em estoque.");
        	    }
        	    found = true;
        	    break;
        	}
        }

        if (!found) {
        	if (quantity <= product.stock) {
        	    cart.add(new CartItem(product, quantity));
        	} else if (product.stock > 0) {
        	    cart.add(new CartItem(product, product.stock));
        	    session.setAttribute("cartMessage", "A quantidade de '" + product.name + "' foi ajustada para o máximo em estoque.");
        	} else {
        	    session.setAttribute("cartMessage", "O produto '" + product.name + "' está fora de estoque.");
        	}
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("/erp/ProductListServlet");
    }
}
