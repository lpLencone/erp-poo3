package erp.servlet.admin;

import erp.dao.CategoryDAO;
import erp.model.Category;
import erp.util.LogUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLEncoder;

@WebServlet("/admin/RegisterCategoryServlet")
public class RegisterCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        try {
            String message;

            if (categoryDAO.categoryExists(name)) {
                message = "Erro: A categoria já existe!";
            } else {
                Category category = new Category(name);
                boolean success = categoryDAO.insertCategory(category);

                if (success) {
                    message = "Categoria cadastrada com sucesso!";
                    LogUtil.logActionToDatabase(userId, "Cadastrou nova categoria: " + name, ip, userAgent);
                } else {
                	LogUtil.logActionToDatabase(userId, "Cadastrou não conseguiu cadastrar nova categoria: " + name, ip, userAgent);
                    message = "Erro ao cadastrar categoria.";
                }
            }
            message = URLEncoder.encode(message, "UTF-8");
            response.sendRedirect("manageCategories.jsp?message=" + message);
            
        } catch (Exception e) {
            response.sendRedirect("manageCategories.jsp?message=" + e.getMessage());
        }
    }
}

