package boost.brain.course.common.auth;

import boost.brain.course.common.users.UserRole;
import lombok.Data;

import java.util.Set;

@Data
public class Credentials {
    private String login;
    private String password;
    private Set<UserRole> roles;
}
