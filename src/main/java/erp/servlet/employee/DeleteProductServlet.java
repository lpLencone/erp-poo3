package erp.servlet.employee;

import erp.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/employee/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                int productId = Integer.parseInt(idParam);
                ProductDAO productDAO = new ProductDAO();

                boolean success = productDAO.deleteProduct(productId);

                if (success) {
                    request.getSession().setAttribute("message", "Produto removido com sucesso.");
                } else {
                    request.getSession().setAttribute("message", "Erro ao remover o produto.");
                }
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("message", "ID inválido.");
            }
        } else {
            request.getSession().setAttribute("message", "ID do produto não fornecido.");
        }

        response.sendRedirect("productManagement.jsp");
    }
}
