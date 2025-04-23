package erp.servlet;

import erp.dao.ProductDAO;
import erp.dao.CategoryDAO;
import erp.dao.SupplierDAO;
import erp.model.Product;
import erp.model.Category;
import erp.model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final SupplierDAO supplierDAO = new SupplierDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entity = request.getParameter("entity");
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        switch (entity) {
            case "product":
                handleProduct(action, idParam, request, response);
                break;
            case "category":
                handleCategory(action, idParam, request, response);
                break;
            case "supplier":
                handleSupplier(action, idParam, request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid entity.");
        }
    }

    private void handleProduct(String action, String idParam, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("list".equals(action)) {
            List<Product> products = productDAO.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/admin/products.jsp").forward(request, response);
        } else if ("edit".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            Product product = productDAO.getProductById(id);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/admin/editProduct.jsp").forward(request, response);
        } else if ("delete".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            productDAO.deleteProduct(id);
            response.sendRedirect("admin?entity=product&action=list");
        }
    }

    private void handleCategory(String action, String idParam, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("list".equals(action)) {
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/categories.jsp").forward(request, response);
        } else if ("edit".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            Category category = categoryDAO.getCategoryById(id);
            request.setAttribute("category", category);
            request.getRequestDispatcher("/admin/editCategory.jsp").forward(request, response);
        } else if ("delete".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            categoryDAO.deleteCategory(id);
            response.sendRedirect("admin?entity=category&action=list");
        }
    }

    private void handleSupplier(String action, String idParam, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("list".equals(action)) {
            List<Supplier> suppliers = supplierDAO.getAllSuppliers();
            request.setAttribute("suppliers", suppliers);
            request.getRequestDispatcher("/admin/suppliers.jsp").forward(request, response);
        } else if ("edit".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            Supplier supplier = supplierDAO.getSupplierById(id);
            request.setAttribute("supplier", supplier);
            request.getRequestDispatcher("/admin/editSupplier.jsp").forward(request, response);
        } else if ("delete".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            supplierDAO.deleteSupplier(id);
            response.sendRedirect("admin?entity=supplier&action=list");
        }
    }
}