package erp.servlet.employee;

import erp.dao.SupplierDAO;
import erp.model.Supplier;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/employee/SupplierServlet")
public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SupplierDAO dao = new SupplierDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Supplier supplier = dao.getSupplierById(id);
            request.setAttribute("supplier", supplier);
            RequestDispatcher dispatcher = request.getRequestDispatcher("editSupplier.jsp");
            dispatcher.forward(request, response);
        } else {
            // Redireciona para a lista de fornecedores
            response.sendRedirect("suppliers.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        Supplier supplier = new Supplier(id, name);
        dao.updateSupplier(supplier);

        response.sendRedirect("suppliers.jsp");
    }
}
