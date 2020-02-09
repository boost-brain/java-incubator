package boost.brain.course.frontend.auth.bean;

import boost.brain.course.common.auth.Session;
import boost.brain.course.common.projects.ProjectDto;
import boost.brain.course.common.users.UserDto;
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

    private Map<Integer, ProjectDto> cacheProjects = new HashMap<>();
    private Map<String, UserDto> cacheUsers = new HashMap<>();
}
