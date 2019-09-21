package boost.brain.course.frontend.auth.view.secured;

import boost.brain.course.frontend.auth.bean.AuthLoginBean;
import boost.brain.course.frontend.auth.bean.HttpSessionBean;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named
@RequestScope
@Data
@Log
public class ButtonLogoutView {

    final private HttpSessionBean httpSessionBean;
    final private AuthLoginBean authLoginBean;

    @Inject
    public ButtonLogoutView(HttpSessionBean httpSessionBean, AuthLoginBean authLoginBean) {
        this.httpSessionBean = httpSessionBean;
        this.authLoginBean = authLoginBean;
    }

    public void buttonAction() {
        if (authLoginBean.logout()) {
            this.sessionInvalidate();
            this.redirect("/");
        }
    }
    private void sessionInvalidate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if(session != null) session.invalidate();
        }
    }

    private void redirect(final String path) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (ec != null) {
            try {
                if (!StringUtils.isEmpty(path)) {
                    ec.redirect(path);
                } else {
                    ec.redirect("/");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
