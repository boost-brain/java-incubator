package boost.brain.course.tasks.repository.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int author;
    private String text;
    private long createDate;
    private long updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private TaskEntity taskId;
}
