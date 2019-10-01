package boost.brain.course.common.auth;

import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String gitHabId;
    private String name;
    private int hours;
    private long createDate;
    private long updateDate;
}
