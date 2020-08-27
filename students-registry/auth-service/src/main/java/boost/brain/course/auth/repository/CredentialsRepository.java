package boost.brain.course.auth.repository;

import boost.brain.course.auth.exception.NotFoundException;
import boost.brain.course.auth.repository.entity.CredentialsEntity;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

@Repository
@Transactional
public class CredentialsRepository {
    private final EntityManager entityManager;

    @Autowired
    public CredentialsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
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

        credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUserId(credentials.getLogin());
        credentialsEntity.setRoles(new HashSet<UserRole>() {{
            add(UserRole.UNACTIVATED);
        }});

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            credentialsEntity.setPasswordHash(messageDigest.digest(credentials.getPassword().getBytes()));
            entityManager.persist(credentialsEntity);
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
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

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            credentialsEntity.setPasswordHash(messageDigest.digest(credentials.getPassword().getBytes()));
            credentialsEntity.setRoles(credentials.getRoles());
            entityManager.merge(credentialsEntity);
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
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
        if (StringUtils.isEmpty(login) || roles == null || roles.size() == 0) {
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
