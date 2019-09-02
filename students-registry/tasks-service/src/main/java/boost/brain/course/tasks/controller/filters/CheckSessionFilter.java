package boost.brain.course.tasks.controller.filters;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter
@Log
public class CheckSessionFilter implements Filter {

    @Value("${auth-service-url}")
    private String AUTH_URL;

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("INIT CheckSessionFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        log.info("DO CheckSessionFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String sessionId = request.getHeader("sessionId");
        if (StringUtils.isEmpty(sessionId)) {
            log.severe(" The header(sessionId) not found!");
            return;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            Boolean sessionIsActive = restTemplate.getForObject(AUTH_URL + sessionId, Boolean.class);
            if (sessionIsActive == null || !sessionIsActive) {
                log.severe("The header(sessionId) is invalid!");
                return;
            }
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            log.severe("CheckSessionFilter throws the exception!");
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        log.info("DESTROY CheckSessionFilter");
    }
}
