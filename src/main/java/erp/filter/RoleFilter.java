package erp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter({"/admin/*", "/employee/*"}) // Protege as páginas sob /admin/ e /employee/
public class RoleFilter implements Filter {

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        String uri = httpRequest.getRequestURI();

        if (uri.startsWith("/erp/admin/")) {
            // Restrito a admin e gerente
            if (userRole == null || (!userRole.equals("Administrador") && !userRole.equals("Gerente"))) {
                httpResponse.sendRedirect("/erp/unauthorized.html"); // Acesso não autorizado
                return;
            }
        } else if (uri.startsWith("/erp/employee/")) {
            // Restrito a admin, gerente e funcionário
            if (userRole == null || (!userRole.equals("Administrador") && !userRole.equals("Gerente") && !userRole.equals("Funcionario"))) {
                httpResponse.sendRedirect("/erp/unauthorized.html"); // Acesso não autorizado
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {}
}
