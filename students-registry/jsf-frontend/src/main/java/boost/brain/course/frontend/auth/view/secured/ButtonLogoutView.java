package boost.brain.course.frontend.auth.view.secured;

import boost.brain.course.common.auth.Session;
import boost.brain.course.frontend.auth.bean.AuthLoginBean;
import boost.brain.course.frontend.auth.bean.HttpSessionBean;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.web.context.annotation.RequestScope;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
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
            httpSessionBean.setSession(new Session());
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            try {
                ec.redirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
