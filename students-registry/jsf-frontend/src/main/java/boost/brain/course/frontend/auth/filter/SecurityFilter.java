package boost.brain.course.frontend.auth.filter;

import boost.brain.course.frontend.auth.bean.AuthLoginBean;
import boost.brain.course.frontend.auth.bean.HttpSessionBean;
import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/secured/*"})
@Log
public class SecurityFilter implements Filter {

    private AuthLoginBean authLoginBean;
    private HttpSessionBean httpSessionBean;

    @Inject
    public SecurityFilter(AuthLoginBean authLoginBean, HttpSessionBean httpSessionBean) {
        this.authLoginBean = authLoginBean;
        this.httpSessionBean = httpSessionBean;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Init security filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Do security filter");
        if (!authLoginBean.checkSession()) {
            HttpServletRequest request = (HttpServletRequest)  servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String path = request.getServletPath();
            httpSessionBean.setPath(path);
            response.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("Destroy security filter");
    }
}
