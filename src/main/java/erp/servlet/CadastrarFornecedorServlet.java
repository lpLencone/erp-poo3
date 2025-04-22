package erp.servlet;

import erp.dao.SupplierDAO;
import erp.model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/CadastrarFornecedorServlet")
public class CadastrarFornecedorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SupplierDAO supplierDAO;

    @Override
    public void init() throws ServletException {
        supplierDAO = new SupplierDAO(); // Inicializa o DAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String contato = request.getParameter("contato");

        Supplier supplier = new Supplier();
        supplier.setName(nome);
        supplier.setContactInfo(contato);

        boolean sucesso = supplierDAO.createSupplier(supplier);

        if (sucesso) {
            // Redireciona para o JSP com uma mensagem de sucesso
            response.sendRedirect("cadastrar_fornecedor.jsp?mensagem=Fornecedor cadastrado com sucesso!");
        } else {
            // Redireciona com mensagem de erro
            response.sendRedirect("cadastrar_fornecedor.jsp?mensagem=Erro ao cadastrar fornecedor.");
        }
    }
}
