package erp.servlet;

import erp.dao.SupplierDAO;
import erp.model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/RegisterSupplierServlet")
public class RegisterSupplierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SupplierDAO supplierDAO;

    @Override
    public void init() throws ServletException {
        supplierDAO = new SupplierDAO(); // Initialize DAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        Supplier supplier = new Supplier(name);
        boolean success = supplierDAO.insertSupplier(supplier);

        if (success) {
            response.sendRedirect("cadastrar_fornecedor.jsp?mensagem=Fornecedor cadastrado com sucesso!");
        } else {
            response.sendRedirect("cadastrar_fornecedor.jsp?mensagem=Erro ao cadastrar fornecedor.");
        }
    }
}
