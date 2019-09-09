package boost.brain.course.frontend.auth.bean;

import boost.brain.course.common.auth.Session;
import lombok.Data;
import org.springframework.web.context.annotation.SessionScope;

import javax.inject.Named;

@Data
@SessionScope
@Named("httpSessionBean")
public class HttpSessionBean {

    private Session session;
}
