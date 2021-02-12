package boost.brain.course.common.auth.bean;


import boost.brain.course.common.auth.SessionCheck;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.*;
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
public class CheckHeaderSessionImpl implements CheckHeaderSession {

    private String authUrl;
    private List<String> skipUriPatterns;
    private RestTemplate restTemplate;

    public CheckHeaderSessionImpl(String authUrl, RestTemplate restTemplate) {
        this.authUrl = authUrl;
        this.restTemplate = restTemplate;

    }

    public CheckHeaderSessionImpl(String authUrl, List<String> skipUriPatterns, RestTemplate restTemplate) {
        this.authUrl = authUrl;
        this.skipUriPatterns = skipUriPatterns;
        this.restTemplate = restTemplate;
    }

    public void check(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("DO CheckHeaderSessionBean.check");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setAttribute("user-email","");
        request.setAttribute("user-roles",null);

        boolean checkSkip = this.checkSkipUriPatterns(request.getRequestURI(), skipUriPatterns);
        if (checkSkip) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        String sessionId = request.getHeader("sessionId");
        if (StringUtils.isEmpty(sessionId)) {
            log.severe(" The header(sessionId) not found!");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("sessionId", sessionId);

            ResponseEntity<SessionCheck> responseEntity = restTemplate.exchange(
                    authUrl,
                    HttpMethod.POST,
                    new HttpEntity(headers),
                    SessionCheck.class
            );
            SessionCheck sessionCheck = responseEntity.getBody();

            if (sessionCheck == null || StringUtils.isEmpty(sessionCheck.getEmail()) || sessionCheck.getRoles() == null) {
                log.severe("The header(sessionId) is invalid! ");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
            request.setAttribute("user-email", sessionCheck.getEmail());
            request.setAttribute("user-roles", sessionCheck.getRoles());
        } catch (Exception e) {
            log.severe("CheckHeaderSessionBean throws the exception!");
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private boolean checkSkipUriPatterns(String requestURI, List<String> skipUriPatterns) {
        if (StringUtils.isEmpty(requestURI) || skipUriPatterns == null) {
            return false;
        }
        return skipUriPatterns.stream().anyMatch(requestURI::matches);
    }
}
