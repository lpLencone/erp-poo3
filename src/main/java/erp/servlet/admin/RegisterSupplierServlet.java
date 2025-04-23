package erp.servlet.admin;

import erp.dao.SupplierDAO;
import erp.model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/RegisterSupplierServlet")
public class RegisterSupplierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SupplierDAO supplierDAO;

    @Override
    public void init() throws ServletException {
        supplierDAO = new SupplierDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");

        try {
            if (supplierDAO.existsByName(name)) {
                // Fornecedor já existe
                response.sendRedirect("registerSupplier.jsp?mensagem=Erro: fornecedor já existe.");
                return;
            }

            Supplier supplier = new Supplier(name);
            boolean success = supplierDAO.insertSupplier(supplier);

            if (success) {
                response.sendRedirect("registerSupplier.jsp?mensagem=Fornecedor cadastrado com sucesso!");
            } else {
                response.sendRedirect("registerSupplier.jsp?mensagem=Erro ao cadastrar fornecedor.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("registerSupplier.jsp?mensagem=Erro interno: " + e.getMessage());
        }
    }
}
