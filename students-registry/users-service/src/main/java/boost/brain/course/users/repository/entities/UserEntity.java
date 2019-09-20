package boost.brain.course.users.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity {

    @Id
    private String email;
    private String gitHabId;
    private String name;
    private int hours;
    private long createDate;
    private long updateDate;

}
