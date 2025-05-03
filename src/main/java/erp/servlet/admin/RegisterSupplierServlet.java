package erp.servlet.admin;

import erp.dao.SupplierDAO;
import erp.model.Supplier;
import erp.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

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

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        int id = 0;

        try {
            if (supplierDAO.existsByName(name)) {
                String message = URLEncoder.encode("Erro: fornecedor já existe.", "UTF-8");
                LogUtil.logActionToDatabase(userId, "Tentou cadastrar fornecedor existente de nome " + name, ip, userAgent);
                response.sendRedirect("manageSuppliers.jsp?message=" + message);
                return;
            }

            Supplier supplier = new Supplier(name);
            boolean success = supplierDAO.insertSupplier(supplier);

            if (success) {
                String message = URLEncoder.encode("Fornecedor cadastrado com sucesso!", "UTF-8");
                LogUtil.logActionToDatabase(userId, "Cadastrou o(a) fornecedor(a) " + name, ip, userAgent);
                response.sendRedirect("manageSuppliers.jsp?message=" + message);
            } else {
                String message = URLEncoder.encode("Erro ao cadastrar fornecedor.", "UTF-8");
                LogUtil.logActionToDatabase(userId, "Não conseguiu cadastrar o(a) fornecedor(a) " + name, ip, userAgent);
                response.sendRedirect("manageSuppliers.jsp?message=" + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String message = URLEncoder.encode("Erro interno: " + e.getMessage(), "UTF-8");
            response.sendRedirect("manageSuppliers.jsp?message=" + message);
        }
    }
}
