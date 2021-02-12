package boost.brain.course.common.auth;

import lombok.Data;

import java.util.Set;

@Data
public class SessionCheck {
    private String email;
    Set<UserRole> roles;
}
