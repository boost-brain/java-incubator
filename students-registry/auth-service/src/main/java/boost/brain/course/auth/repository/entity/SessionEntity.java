package boost.brain.course.auth.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SessionEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(insertable = false, updatable = false, nullable = false)
    private String id;
    private long startTime;
    private long validTime;
    private boolean active;

    @ManyToOne
    private CredentialsEntity credentialsEntity;
}
