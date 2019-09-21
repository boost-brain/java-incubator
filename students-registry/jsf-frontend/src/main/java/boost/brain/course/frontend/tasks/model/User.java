package boost.brain.course.frontend.tasks.model;


import lombok.Data;

@Data
public class User {

    private String email;
    private String gitHabId;
    private String name;
    private int hours;
    private long createDate;
    private long updateDate;
}
