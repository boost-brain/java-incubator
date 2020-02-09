package boost.brain.course.common.tasks;

import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private long taskId;
    private String author;
    private String text;
    private long createDate;
    private long updateDate;
}
