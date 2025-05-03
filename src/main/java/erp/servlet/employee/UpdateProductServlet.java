package erp.servlet.employee;
import erp.util.LogUtil;

import erp.dao.ProductDAO;
import erp.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/employee/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            int userId = (int) session.getAttribute("userId");
            String name = request.getParameter("name");
            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");

            int id = Integer.parseInt(request.getParameter("id"));
            double price = Double.parseDouble(request.getParameter("price"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            Product updatedProduct = new Product(id, name, price, categoryId, stock);

            ProductDAO productDAO = new ProductDAO();
            boolean success = productDAO.updateProduct(updatedProduct);

            if (success) {
                LogUtil.logActionToDatabase(userId, "Alterou o Produto de  nome: "+ name, ip, userAgent);                
                response.sendRedirect("productManagement.jsp");
            } else {
                LogUtil.logActionToDatabase(userId, "Nao alterou o Produto de  nome: "+ name, ip, userAgent);                
                request.setAttribute("error", "Erro ao atualizar produto.");
                request.getRequestDispatcher("productManagement.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erro ao processar a requisição.");
            request.getRequestDispatcher("productManagement.jsp").forward(request, response);
        }
    }
}
