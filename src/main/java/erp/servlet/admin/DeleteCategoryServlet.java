package erp.servlet.admin;

import erp.dao.CategoryDAO;
import erp.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

@WebServlet("/admin/DeleteCategoryServlet")
public class DeleteCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String message;
        
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        
        int id = 0;
        try {
            id = Integer.parseInt(idParam);
            boolean success = categoryDAO.deleteCategoryById(id);

            if (success) {
                message = "Categoria excluída com success!";
                LogUtil.logActionToDatabase(userId, "Excluiu categoria de id " + id, ip, userAgent);
            } else {
                message = "Erro: categoria não encontrada ou não pôde ser excluída.";
                LogUtil.logActionToDatabase(userId, "Não conseguiu excluir categoria de id " + id, ip, userAgent);
            }

        } catch (SQLException e) {
        	LogUtil.logActionToDatabase(userId, "Não conseguiu excluir categoria de id " + id, ip, userAgent);
            e.printStackTrace();

            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                // 23XXX → Violação de integridade referencial
                message = "Erro: a categoria está associada a outros registros e não pode ser excluída.";
            } else {
                message = "Erro interno ao excluir categoria: " + e.getMessage();
            }

        } catch (NumberFormatException e) {
            message = "ID de categoria inválido.";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erro inesperado: " + e.getMessage();
        }

        response.sendRedirect("manageCategories.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
    }
}
