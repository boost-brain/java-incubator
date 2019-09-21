package boost.brain.course.frontend.auth.bean;

import boost.brain.course.common.auth.Session;
import boost.brain.course.common.auth.UserDto;
import boost.brain.course.frontend.tasks.model.Project;
import lombok.Data;
import org.springframework.web.context.annotation.SessionScope;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Data
@SessionScope
@Named
public class HttpSessionBean {

    private Session session;
    private String login;
    private String path;
    private long lastUpdateDate;

    private Map<Integer, Project> cacheProjects = new HashMap<>();
    private Map<String, UserDto> cacheUsers = new HashMap<>();
}
