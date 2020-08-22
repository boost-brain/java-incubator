package boost.brain.course.common.auth.filter;

import boost.brain.course.common.auth.bean.CheckHeaderSession;
import lombok.Data;
import lombok.extern.java.Log;

import javax.servlet.*;
import java.io.IOException;

@Log
@Data
public class CheckSessionFilter implements Filter {

    private CheckHeaderSession checkHeaderSession;

    public CheckSessionFilter(CheckHeaderSession checkHeaderSession) {
        this.checkHeaderSession = checkHeaderSession;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("INIT CheckSessionFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("DO CheckSessionFilter");
        checkHeaderSession.check(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void destroy() {
        log.info("DESTROY CheckSessionFilter");
    }
}
