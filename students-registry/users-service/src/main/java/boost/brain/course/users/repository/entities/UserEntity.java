package boost.brain.course.users.repository.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserEntity {

    @Id
    private String email;
    private String gitHabId;
    private String name;
    private int hours;
    private long createDate;
    private long updateDate;

}
