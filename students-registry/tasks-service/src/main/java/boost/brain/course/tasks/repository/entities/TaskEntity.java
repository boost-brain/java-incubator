package boost.brain.course.tasks.repository.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
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
}