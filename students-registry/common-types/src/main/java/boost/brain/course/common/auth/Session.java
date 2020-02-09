package boost.brain.course.common.auth;

import lombok.Data;

@Data
public class Session {
    private String sessionId;
    private long startTime;
    private long validTime;
}
