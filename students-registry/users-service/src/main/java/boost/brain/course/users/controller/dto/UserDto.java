package boost.brain.course.users.controller.dto;

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
