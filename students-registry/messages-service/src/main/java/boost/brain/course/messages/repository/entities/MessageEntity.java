package boost.brain.course.messages.repository.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String author;
    private String recipient;
    private String text;
    private long createDate;
    private long editDate;
    private long readDate;
    private boolean edited;
    private boolean read;

}
