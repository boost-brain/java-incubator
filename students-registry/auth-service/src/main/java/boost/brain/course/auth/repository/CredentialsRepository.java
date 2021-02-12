package boost.brain.course.auth.repository;

import boost.brain.course.auth.exception.NotFoundException;
import boost.brain.course.auth.repository.entity.CredentialsEntity;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

@Repository
@Transactional
public class CredentialsRepository {
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CredentialsRepository(EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Set<UserRole> readUserRoles(String login) {
        if (StringUtils.isEmpty(login)) {
            throw new NotFoundException();
        }

        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, login);
        if (credentialsEntity == null) {
            throw new NotFoundException();
        }

        return credentialsEntity.getRoles();
    }

    public boolean create(Credentials credentials) {
        if (credentials == null ||
                StringUtils.isEmpty(credentials.getLogin()) ||
                StringUtils.isEmpty(credentials.getPassword())) {
            return false;
        }

        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, credentials.getLogin());
        if (credentialsEntity != null) {
            return false;
        }

        CredentialsEntity newCredentialsEntity = new CredentialsEntity();
        newCredentialsEntity.setUserId(credentials.getLogin());
        newCredentialsEntity.setRoles(new HashSet<UserRole>() {{
            add(UserRole.UNACTIVATED);
        }});
        newCredentialsEntity.setPasswordHash(passwordEncoder.encode(credentials.getPassword()));
        entityManager.persist(newCredentialsEntity);

        return true;
    }

    public boolean update(Credentials credentials) {
        if (credentials == null ||
                StringUtils.isEmpty(credentials.getLogin()) ||
                StringUtils.isEmpty(credentials.getPassword())) {
            return false;
        }

        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, credentials.getLogin());
        if (credentialsEntity == null) {
            return false;
        }
        credentialsEntity.setPasswordHash(passwordEncoder.encode(credentials.getPassword()));
        entityManager.merge(credentialsEntity);

        return true;
    }

    public boolean delete(String login) {
        if (StringUtils.isEmpty(login)) {
            return false;
        }

        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, login);
        if (credentialsEntity == null) {
            return false;
        }

        entityManager.remove(credentialsEntity);
        return true;
    }


    public boolean updateUserRoles(Set<UserRole> roles, String login) {
        if (StringUtils.isEmpty(login) || roles == null || roles.size() == 0 || roles.contains(UserRole.ADMINISTRATOR)) {
            return false;
        }

        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, login);
        if (credentialsEntity == null) {
            return false;
        }

        credentialsEntity.setRoles(roles);
        entityManager.merge(credentialsEntity);
        return true;
    }
}
