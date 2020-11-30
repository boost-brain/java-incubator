package boost.brain.course.auth.repository;


import boost.brain.course.auth.exception.NotFoundException;
import boost.brain.course.auth.repository.entity.CredentialsEntity;
import boost.brain.course.auth.repository.entity.SessionEntity;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import boost.brain.course.common.auth.SessionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
@Transactional
public class SessionsRepository {

    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SessionsRepository(EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    public SessionCheck getCheckSession(String sessionId) {
        SessionCheck result = new SessionCheck();

        if (StringUtils.isEmpty(sessionId)) {
            throw new NotFoundException();
        }

        SessionEntity sessionEntity = entityManager.find(SessionEntity.class, sessionId);
        if (sessionEntity == null || !sessionEntity.isActive()){
            throw new NotFoundException();
        }

        CredentialsEntity credentialsEntity = sessionEntity.getCredentialsEntity();

        result.setEmail(credentialsEntity.getUserId());
        result.setRoles(credentialsEntity.getRoles());

        return result;
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

        if (!passwordEncoder.matches(credentials.getPassword(), credentialsEntity.getPasswordHash())) {
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

    public boolean closeSession(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return false;
        }

        SessionEntity sessionEntity = entityManager.find(SessionEntity.class, sessionId);
        if(sessionEntity == null) {
            return false;
        }

        sessionEntity.setActive(false);
        entityManager.merge(sessionEntity);

        return true;
    }

    public boolean checkSession(String sessionId){
        if (StringUtils.isEmpty(sessionId)) {
            return false;
        }

        SessionEntity sessionEntity = entityManager.find(SessionEntity.class, sessionId);
        if(sessionEntity == null){
            return false;
        }

        return sessionEntity.isActive();
    }

    public Map<String, Long> getEmailsWithStartTimeMap() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CredentialsEntity> cq = cb.createQuery(CredentialsEntity.class);
        Root<CredentialsEntity> from = cq.from(CredentialsEntity.class);
        CriteriaQuery<CredentialsEntity> select = cq.select(from);
        TypedQuery<CredentialsEntity> typedQuery = entityManager.createQuery(select);
        List<CredentialsEntity> credentialsEntities = typedQuery.getResultList();

        Map<String, Long> result = new HashMap<>();
        for (CredentialsEntity credentialsEntity: credentialsEntities) {
            long startTime = this.getMaxStartTime(credentialsEntity);
            if (startTime > 0) {
                result.put(credentialsEntity.getUserId(),startTime);
            }
        }
        return result;
    }

    private long getMaxStartTime(CredentialsEntity credentialsEntity) {
        long result = 0;
        Map<String, SessionEntity> sessionEntities = credentialsEntity.getSessionEntities();
        for (SessionEntity sessionEntity : sessionEntities.values()) {
            if (result < sessionEntity.getStartTime()) {
                result = sessionEntity.getStartTime();
            }
        }
        return result;
    }
}
