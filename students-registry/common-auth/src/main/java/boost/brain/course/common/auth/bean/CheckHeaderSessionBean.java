package boost.brain.course.common.auth.bean;


import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Log
@Data
public class CheckHeaderSessionBean implements CheckHeaderSession {

    private String authUrl;

    public CheckHeaderSessionBean(String authUrl) {
        this.authUrl = authUrl;
    }

    public void check(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        log.info("DO CheckHeaderSessionBean.check");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String sessionId = request.getHeader("sessionId");
        if (StringUtils.isEmpty(sessionId)) {
            log.severe(" The header(sessionId) not found!");
            return;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            Boolean sessionIsActive = restTemplate.getForObject(authUrl + sessionId, Boolean.class);
            if (sessionIsActive == null || !sessionIsActive) {
                log.severe("The header(sessionId) is invalid!");
                return;
            }
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            log.severe("CheckHeaderSessionBean throws the exception!");
            e.printStackTrace();
        }
    }
}
