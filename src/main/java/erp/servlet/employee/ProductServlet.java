package erp.servlet.employee;

import erp.dao.ProductDAO;
import erp.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/employee/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductDAO productDAO = new ProductDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                Product editProduct = productDAO.getProductById(editId);
                request.setAttribute("product", editProduct);
                request.getRequestDispatcher("editProduct.jsp").forward(request, response);
                break;
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                productDAO.deleteProduct(deleteId);
                response.sendRedirect("ProductServlet");
                break;
            default:
                List<Product> products = productDAO.getAllProducts();
                request.setAttribute("products", products);
                request.getRequestDispatcher("manageProducts.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        
        Product product = new Product(id, name, description, price, categoryId, stock);

        if (id > 0) {
            productDAO.updateProduct(product);
        } else {
            productDAO.insertProduct(product);
        }

        response.sendRedirect("ProductServlet");
    }
}