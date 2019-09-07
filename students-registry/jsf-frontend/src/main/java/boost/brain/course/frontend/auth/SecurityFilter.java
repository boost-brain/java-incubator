package boost.brain.course.frontend.auth;

import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/secured/*"})
@Log
public class SecurityFilter implements Filter {

    private AuthLoginBean  authLoginBean;

    @Inject
    public SecurityFilter(@Named("authLoginBean")AuthLoginBean authLoginBean) {
        this.authLoginBean = authLoginBean;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Init security filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("DO SECURITY FILTER");
        if (!authLoginBean.checkSession()) {
            HttpServletRequest request = (HttpServletRequest)  servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("Destroy security filter");
    }
}
