package boost.brain.course.common.auth.bean;


import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log
@Data
public class CheckHeaderSessionBean implements CheckHeaderSession {

    private String authUrl;
    private String skipUrlPatterns;

    public CheckHeaderSessionBean(String authUrl) {
        this.authUrl = authUrl;
    }


    public CheckHeaderSessionBean(String authUrl, String skipUrlPatterns) {
        this.authUrl = authUrl;
        this.skipUrlPatterns = skipUrlPatterns;
    }

    public void check(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        if (servletRequest.getLocalAddr().contentEquals(skipUrlPatterns)) {
            System.out.println("Работает отмена фильтра");
            try {
                filterChain.doFilter(servletRequest,servletResponse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                log.severe("CheckHeaderSessionBean throws the exception!");
                e.printStackTrace();
                this.setHttpStatusForResponse(servletResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return;
        }

        log.info("DO CheckHeaderSessionBean.check");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String sessionId = request.getHeader("sessionId");
        if (StringUtils.isEmpty(sessionId)) {
            log.severe(" The header(sessionId) not found!");
            this.setHttpStatusForResponse(servletResponse, HttpStatus.FORBIDDEN);
            return;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            Boolean sessionIsActive = restTemplate.getForObject(authUrl + sessionId, Boolean.class);
            if (sessionIsActive == null || !sessionIsActive) {
                log.severe("The header(sessionId) is invalid!" + authUrl + sessionId);
                this.setHttpStatusForResponse(servletResponse, HttpStatus.FORBIDDEN);
                return;
            }
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            log.severe("CheckHeaderSessionBean throws the exception!");
            e.printStackTrace();
            this.setHttpStatusForResponse(servletResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void setHttpStatusForResponse(ServletResponse servletResponse, HttpStatus status) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(status.value());
    }
}
