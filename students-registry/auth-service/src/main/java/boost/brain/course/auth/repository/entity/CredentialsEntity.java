package boost.brain.course.auth.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class CredentialsEntity {
    @Id
    private String userId;
    private byte[] passwordHash;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "credentialsEntity")
    @MapKey(name = "id")
    private Map<String, SessionEntity> sessionEntities;
}
