package boost.brain.course.frontend.tasks.model;

import lombok.Data;

@Data
public class Task {

    private long id;
    private int project;
    private String author;
    private String implementer;
    private String name;
    private String text;
    private long createDate;
    private long updateDate;
}
