package erp.servlet.employee;

import erp.dao.CategoryDAO;
import erp.model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/employee/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO = new CategoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                Category editCategory = categoryDAO.getCategoryById(editId);
                request.setAttribute("category", editCategory);
                request.getRequestDispatcher("editCategory.jsp").forward(request, response);
                break;
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                categoryDAO.deleteCategory(deleteId);
                response.sendRedirect("CategoryServlet");
                break;
            default:
                List<Category> categories = categoryDAO.getAllCategories();
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("manageCategories.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;
        String name = request.getParameter("name");

        Category category = new Category(id, name);

        if (id > 0) {
            categoryDAO.updateCategory(category);
        } else {
            try {
				categoryDAO.insertCategory(category);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }

        response.sendRedirect("CategoryServlet");
    }
}