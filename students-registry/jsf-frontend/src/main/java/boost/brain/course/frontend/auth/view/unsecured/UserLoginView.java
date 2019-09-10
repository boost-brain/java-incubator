package boost.brain.course.frontend.auth.view.unsecured;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.frontend.auth.bean.AuthLoginBean;
import boost.brain.course.frontend.auth.bean.HttpSessionBean;
import lombok.Data;
import lombok.extern.java.Log;
import org.primefaces.PrimeFaces;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScope
@Data
@Log
public class UserLoginView {

    private String username;
    private String password;

    final private HttpSessionBean httpSessionBean;
    final private AuthLoginBean authLoginBean;

    @Inject
    public UserLoginView(HttpSessionBean httpSessionBean, AuthLoginBean authLoginBean) {
        this.httpSessionBean = httpSessionBean;
        this.authLoginBean = authLoginBean;
    }

    public void login() {
        log.info("DO LOGIN UserLoginView");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            this.errorMessage();
            return;
        }
        Credentials credentials = new Credentials();
        credentials.setLogin(username);
        credentials.setPassword(password);
        authLoginBean.doLogin(credentials);
        if (httpSessionBean.getSession() == null
                || StringUtils.isEmpty(httpSessionBean.getSession().getSessionId())) {
            this.errorMessage();
            return;
        }
        this.confirmed();
    }

    private void errorMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        boolean loggedIn = false;
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
    }

    private void confirmed() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Welcome", "");
        boolean loggedIn = true;
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String path = httpSessionBean.getPath();
        try {
            if (StringUtils.isEmpty(path)) {

                ec.redirect("secured/main");
            } else {
                httpSessionBean.setPath("");
                ec.redirect(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
