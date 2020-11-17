package boost.brain.course.auth.repository.entity;

import boost.brain.course.common.users.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
public class CredentialsEntity {
    @Id
    @Email
    private String userId;
    private String passwordHash;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "credentialsEntity")
    @MapKey(name = "id")
    private Map<String, SessionEntity> sessionEntities;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;
}
