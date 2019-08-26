package boost.brain.course.tasks.repository.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int project;
    private int author;
    private int implementer;
    private String name;
    private String text;
    private long createDate;
    private long updateDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "taskId")
    private List<CommentEntity> comments = new ArrayList<>();
}