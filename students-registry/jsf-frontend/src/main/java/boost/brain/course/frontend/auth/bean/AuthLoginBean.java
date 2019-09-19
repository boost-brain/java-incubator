package boost.brain.course.frontend.auth.bean;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;

@Data
@Named
@Log
public class AuthLoginBean {

    private String authUrl;
    final private HttpSessionBean httpSessionBean;

    @Inject
    public AuthLoginBean(@Value("${auth-login-url}")String authUrl, HttpSessionBean httpSessionBean) {
        this.authUrl = authUrl;
        this.httpSessionBean = httpSessionBean;
    }

    public void doLogin(final Credentials credentials) {
        log.info("Do login");
        if(credentials == null) {
            return;
        }
        try {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
            RestTemplate restTemplate = restTemplateBuilder.build();
            HttpEntity<Credentials> credentialsRequest = new HttpEntity<>(credentials);
            Session session = restTemplate.postForObject(authUrl +"/login", credentialsRequest, Session.class);
            if (session == null || StringUtils.isEmpty(session.getSessionId())) {
                log.severe("The session not found!");
            } else {
                httpSessionBean.setSession(session);
                httpSessionBean.setLogin(credentials.getLogin());
            }
        } catch (Exception e) {
            log.severe("AuthLoginBean.doLogin throws Exception!");
            e.printStackTrace();
        }
    }

    public boolean checkSession() {
        log.info("Do check session");
        Boolean result = false;
        if (httpSessionBean.getSession() == null ||
                StringUtils.isEmpty(httpSessionBean.getSession().getSessionId())) {
            log.severe("The sessionId not found!");
            return result;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            Boolean sessionIsActive = restTemplate.getForObject(authUrl
                    + "/check/" + httpSessionBean.getSession().getSessionId(), Boolean.class);
            if (sessionIsActive != null && sessionIsActive) {
                result = true;
            } else {
                log.severe("The sessionId is invalid!");
            }
        } catch (Exception e) {
            log.severe("AuthLoginBean.checkSession throws the exception!");
            e.printStackTrace();
        }
        return result;
    }

    public boolean logout() {
        log.info("Do logout");
        Boolean result = false;
        if (httpSessionBean.getSession() == null ||
                StringUtils.isEmpty(httpSessionBean.getSession().getSessionId())) {
            log.severe("The sessionId not found!");
            return result;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            Boolean sessionIsActive = restTemplate.getForObject(authUrl
                    + "/logout/" + httpSessionBean.getSession().getSessionId(), Boolean.class);
            if (sessionIsActive != null && sessionIsActive) {
                result = true;
            } else {
                log.severe("The sessionId is invalid!");
            }
        } catch (Exception e) {
            log.severe("AuthLoginBean.logout throws the exception!");
            e.printStackTrace();
        }
        return result;
    }
}
