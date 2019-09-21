package boost.brain.course.frontend.users.model;

import lombok.Data;

import javax.inject.Named;

@Data
public class User {

    private String name;
    private String email;
    private String gitHabId;
    private int hours;
    private long createDate;
    private long updateDate;

}
