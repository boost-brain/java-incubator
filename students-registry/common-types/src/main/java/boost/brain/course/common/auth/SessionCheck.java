package boost.brain.course.common.auth;

import boost.brain.course.common.users.UserRole;
import lombok.Data;

import java.util.Set;

@Data
public class SessionCheck {
    private String email;
    Set<UserRole> roles;
}
