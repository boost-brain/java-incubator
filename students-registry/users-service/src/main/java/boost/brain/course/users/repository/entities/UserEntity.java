package boost.brain.course.users.repository.entities;

import boost.brain.course.common.users.UserStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Data
public class UserEntity {

    @Id
    private String email;
    private String gitHubId;
    private String name;
    private int hours;
    private long createDate;
    private long updateDate;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

}
