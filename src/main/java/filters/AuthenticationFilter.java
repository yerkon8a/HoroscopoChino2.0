package filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");
        boolean isLoginAction = httpRequest.getRequestURI().endsWith("login");
        boolean isRegistroPage = httpRequest.getRequestURI().endsWith("registro.jsp");
        boolean isRegistroAction = httpRequest.getRequestURI().endsWith("registro");

        if (isLoggedIn || isLoginPage || isLoginAction || isRegistroPage || isRegistroAction) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Limpieza del filtro
    }
}