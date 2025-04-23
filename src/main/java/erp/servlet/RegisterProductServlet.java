package erp.servlet;

import erp.dao.ProductDAO;
import erp.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/RegisterProductServlet")
public class RegisterProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String message = null;

        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            int supplierId = Integer.parseInt(request.getParameter("supplier_id"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            Product product = new Product(name, description, price, categoryId, stock);
            ProductDAO productDAO = new ProductDAO();
            boolean success = productDAO.createProduct(product, supplierId);

            if (success) {
                message = "Produto cadastrado com sucesso!";
            } else {
                message = "Erro ao cadastrar produto.";
            }
        } catch (Exception e) {
            message = "Erro: " + e.getMessage();
            e.printStackTrace();
        }

        // Adiciona a mensagem no request e encaminha de volta para o JSP
        request.setAttribute("message", message);
        request.getRequestDispatcher("/registerProduct.jsp").forward(request, response);
    }
}
