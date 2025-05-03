package erp.servlet.admin;

import erp.dao.SupplierDAO;
import erp.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URLEncoder;

@WebServlet("/admin/DeleteSupplierServlet")
public class DeleteSupplierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SupplierDAO supplierDAO;

    @Override
    public void init() throws ServletException {
        supplierDAO = new SupplierDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String supplierIdStr = request.getParameter("id");
        
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        try {
            int supplierId = Integer.parseInt(supplierIdStr);

            boolean success = supplierDAO.deleteSupplierById(supplierId);

            if (success) {
            	LogUtil.logActionToDatabase(userId, "Excluiu fornecedor de id " + supplierId, ip, userAgent);
                String mensagem = URLEncoder.encode("Fornecedor excluído com sucesso!", "UTF-8");
                response.sendRedirect("manageSuppliers.jsp?mensagem=" + mensagem);
            } else {
            	LogUtil.logActionToDatabase(userId, "Não conseguiu excluir fornecedor de id " + supplierId, ip, userAgent);
                String mensagem = URLEncoder.encode("Erro: fornecedor não encontrado.", "UTF-8");
                response.sendRedirect("manageSuppliers.jsp?mensagem=" + mensagem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            String mensagem = URLEncoder.encode("Erro: Não é possível excluir o fornecedor, ele pode estar associado a produtos.", "UTF-8");
            response.sendRedirect("manageSuppliers.jsp?mensagem=" + mensagem);
        } catch (Exception e) {
            e.printStackTrace();
            String mensagem = URLEncoder.encode("Erro interno: " + e.getMessage(), "UTF-8");
            response.sendRedirect("manageSuppliers.jsp?mensagem=" + mensagem);
        }
    }
}
