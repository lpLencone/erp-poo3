package erp.servlet.employee;

import erp.dao.ProductDAO;
import erp.dao.SupplierProductDAO;
import erp.model.Product;
import erp.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/employee/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int supplierId = Integer.parseInt(request.getParameter("supplierId"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Product product = new Product(name, price, categoryId, stock);
        ProductDAO productDAO = new ProductDAO();
        SupplierProductDAO supplierProductDAO = new SupplierProductDAO();

        int productId = productDAO.insertProductAndGetId(product);

        if (productId > 0) {
            boolean relationSuccess = supplierProductDAO.insertSupplierProduct(supplierId, productId);

            if (relationSuccess) {
                LogUtil.logActionToDatabase(userId, "Adicionou o Produto " + name, ip, userAgent);
                response.sendRedirect("productManagement.jsp");
            } else {
                LogUtil.logActionToDatabase(userId, "Falha ao adicionar o Produto " + name, ip, userAgent);
                request.setAttribute("error", "Produto criado, mas falha ao associar fornecedor.");
                request.getRequestDispatcher("addProduct.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Erro ao adicionar produto.");
            request.getRequestDispatcher("addProduct.jsp").forward(request, response);
        }
    }
}
