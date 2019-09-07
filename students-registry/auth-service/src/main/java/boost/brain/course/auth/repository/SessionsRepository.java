package boost.brain.course.auth.repository;


import boost.brain.course.auth.repository.entity.CredentialsEntity;
import boost.brain.course.auth.repository.entity.SessionEntity;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional
public class SessionsRepository {

    private final EntityManager entityManager;

    @Autowired
    public SessionsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Session startSession(Credentials credentials) {
        Session result = new Session();

        if (credentials == null) {
            return result;
        }

        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, credentials.getLogin());
        if (credentialsEntity == null) {
            return result;
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] receivedHash = messageDigest.digest(credentials.getPassword().getBytes());
            if (!Arrays.equals(receivedHash, credentialsEntity.getPasswordHash())) {
                return result;
            }
        } catch (NoSuchAlgorithmException e) {
            return result;
        }

        Map<String, SessionEntity> sessionEntities = credentialsEntity.getSessionEntities();
        if (sessionEntities != null && !sessionEntities.isEmpty()) {
            for (SessionEntity sessionEntity : sessionEntities.values()) {
                if (sessionEntity.isActive()) {
                    sessionEntity.setActive(false);
                    entityManager.merge(sessionEntity);
                }
            }
        }

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setId(UUID.randomUUID().toString());
        sessionEntity.setCredentialsEntity(credentialsEntity);
        sessionEntity.setStartTime(System.currentTimeMillis());
        sessionEntity.setValidTime(Long.MAX_VALUE);
        sessionEntity.setActive(true);
        entityManager.persist(sessionEntity);

        result.setSessionId(sessionEntity.getId());
        result.setStartTime(sessionEntity.getStartTime());
        result.setValidTime(sessionEntity.getValidTime());

        return result;
    }

    public boolean closeSession(Session session) {
        if (session == null || StringUtils.isEmpty(session.getSessionId())) {
            return false;
        }

        SessionEntity sessionEntity = entityManager.find(SessionEntity.class, session.getSessionId());
        if(sessionEntity == null){
            return false;
        }

        sessionEntity.setActive(false);
        entityManager.merge(sessionEntity);

        return true;
    }

    public boolean checkSession(Session session){
        if (session == null || StringUtils.isEmpty(session.getSessionId())) {
            return false;
        }

        SessionEntity sessionEntity = entityManager.find(SessionEntity.class, session.getSessionId());
        if(sessionEntity == null){
            return false;
        }

        return sessionEntity.isActive();
    }
}
