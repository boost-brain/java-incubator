package boost.brain.course.auth.repository;

import boost.brain.course.auth.repository.entity.CredentialsEntity;
import boost.brain.course.common.auth.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Repository
@Transactional
public class CredentialsRepository {
    private final EntityManager entityManager;

    @Autowired
    public CredentialsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
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

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            credentialsEntity.setPasswordHash(messageDigest.digest(credentials.getPassword().getBytes()));
            entityManager.persist(credentialsEntity);
            Credentials result = new Credentials();
            result.setLogin(credentialsEntity.getUserId());
            result.setPassword(credentialsEntity.getPasswordHash().toString());
            //BeanUtils.copyProperties(credentialsEntity, result);
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public boolean checkIfExists(Credentials credentials) {
        if (credentials == null ||
                StringUtils.isEmpty(credentials.getLogin())) {
            return false;
        }

        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, credentials.getLogin());
        return credentialsEntity != null;
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

}
