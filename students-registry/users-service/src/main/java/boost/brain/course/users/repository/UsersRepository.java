package boost.brain.course.users.repository;

//import boost.brain.course.users.controller.dto.UserDto;
import boost.brain.course.common.users.UserDto;
import boost.brain.course.users.repository.entities.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UsersRepository {

    private final EntityManager entityManager;

    @Autowired
    public UsersRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserDto create(final UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        entityManager.persist(userEntity);
        UserDto result = new UserDto();
        BeanUtils.copyProperties(userEntity, result);
        return result;
    }

    public UserDto read(final String email) {
        UserEntity userEntity = entityManager.find(UserEntity.class, email);
        if (userEntity == null) {
            return null;
        }
        UserDto result = new UserDto();
        BeanUtils.copyProperties(userEntity, result);
        return result;
    }

    public boolean update(final UserDto userDto) {
        if (userDto == null) {
            return false;
        }
        UserEntity userEntity = entityManager.find(UserEntity.class, userDto.getEmail());
        if (userEntity == null) {
            return false;
        }
        BeanUtils.copyProperties(userDto, userEntity, "createDate", "email");
        entityManager.merge(userEntity);
        return true;
    }

    public boolean delete(final String email) {
        UserEntity userEntity = entityManager.find(UserEntity.class, email);
        if (userEntity == null) {
            return false;
        }
        entityManager.remove(userEntity);
        return true;
    }

    public long count() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(UserEntity.class)));
        Long result = entityManager.createQuery(cq).getSingleResult();
        return result;
    }

    public List<UserDto> getPage(final int pageNumber, final int pageSize) {
        List<UserDto> result = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> from = cq.from(UserEntity.class);
        CriteriaQuery<UserEntity> select = cq.select(from).where();

        TypedQuery<UserEntity> tq = entityManager.createQuery(select);
        tq.setFirstResult((pageNumber - 1) * pageSize);
        tq.setMaxResults(pageSize);

        List<UserEntity> userEntities = tq.getResultList();

        for (UserEntity userEntity: userEntities) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            result.add(userDto);
        }
        return result;
    }

    public List<UserDto> usersForEmails(final List<String> emails) {
        List<UserDto> result = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> from = cq.from(UserEntity.class);

        Predicate where = from.get("email").in(emails);
        CriteriaQuery<UserEntity> select = cq.select(from).where(where);
        TypedQuery<UserEntity> typedQuery = entityManager.createQuery(select);
        List<UserEntity> userEntities = typedQuery.getResultList();

        for (UserEntity userEntity: userEntities) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            result.add(userDto);
        }
        return result;
    }

    public List<UserDto> allUsers() {
        List<UserDto> result = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> from = cq.from(UserEntity.class);

        CriteriaQuery<UserEntity> select = cq.select(from);
        TypedQuery<UserEntity> typedQuery = entityManager.createQuery(select);
        List<UserEntity> userEntities = typedQuery.getResultList();

        for (UserEntity userEntity: userEntities) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            result.add(userDto);
        }
        return result;
    }

    public boolean checkIfExists(final String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        UserEntity userEntity = entityManager.find(UserEntity.class, email);
        return userEntity != null;
    }
}
