package erp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*") // Intercepta todas as requisições
public class AuthFilter implements Filter {

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        System.out.println(uri);
        HttpSession session = httpRequest.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("userId") != null);
        boolean isLoginPage = uri.endsWith("login.jsp") || uri.endsWith("LoginServlet");
        boolean isSingUpPage = uri.endsWith("register.jsp") || uri.endsWith("RegisterServlet");
        boolean isStaticResource = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/");

        if (loggedIn || isLoginPage || isSingUpPage || isStaticResource) {
            // Se o usuário estiver logado ou estiver acessando login ou recurso público
            chain.doFilter(request, response);
        } else {
            // Usuário não autenticado, redireciona para login
            httpResponse.sendRedirect("login.jsp");
        }
    }

    public void destroy() {}
}
