package boost.brain.course.tasks.repository;

import boost.brain.course.common.tasks.TaskDto;
import boost.brain.course.tasks.repository.entities.TaskEntity;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
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
import java.util.Optional;

@Repository
@Transactional
public class TasksRepository{

    private final EntityManager entityManager;

    @Autowired
    public TasksRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<TaskDto> create(final TaskDto taskDto) {
        if (taskDto == null) {
            return Optional.empty();
        }
        TaskEntity taskEntity =saveToBaseAloneTaskDto(taskDto) ;
        TaskDto result = new TaskDto();
        BeanUtils.copyProperties(taskEntity, result, "comments");
        return Optional.of(result);
    }

    private TaskEntity saveToBaseAloneTaskDto(TaskDto taskDto) {
        TaskEntity taskEntity = new TaskEntity();
        BeanUtils.copyProperties(taskDto, taskEntity, "id");
        entityManager.persist(taskEntity);
        return taskEntity;
    }

    public Optional<TaskDto> read(final long id) {
        TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);
        if (taskEntity == null) {
            return Optional.empty();
        }
        TaskDto result = new TaskDto();
        BeanUtils.copyProperties(taskEntity, result);
        return Optional.of(result);
    }

    public boolean update(final TaskDto taskDto) {
        if (taskDto == null) {
            return false;
        }
        TaskEntity taskEntity = entityManager.find(TaskEntity.class, taskDto.getId());
        if (taskEntity == null) {
            return false;
        }
        BeanUtils.copyProperties(taskDto, taskEntity, "createDate", "author");
        entityManager.merge(taskEntity);
        return true;
    }

    public boolean delete(final long id) {
        TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);
        if (taskEntity == null) {
            return false;
        }
        entityManager.remove(taskEntity);
        return true;
    }

    public long count() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(TaskEntity.class)));
        Long result = entityManager.createQuery(countQuery).getSingleResult();
        return result;
    }

    public List<TaskDto> getPage(final int pageNumber, final int pageSize) {
        List<TaskDto> result = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<TaskEntity> criteriaQuery = criteriaBuilder.createQuery(TaskEntity.class);
        Root<TaskEntity> from = criteriaQuery.from(TaskEntity.class);
        CriteriaQuery<TaskEntity> select = criteriaQuery.select(from).where();

        TypedQuery<TaskEntity> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult((pageNumber - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        List<TaskEntity> taskEntities = typedQuery.getResultList();

        for (TaskEntity taskEntity: taskEntities) {
            TaskDto taskDto = new TaskDto();
            BeanUtils.copyProperties(taskEntity, taskDto, "comments");
            result.add(taskDto);
        }
        return result;
    }

    public List<TaskDto> tasksFor(final String implementer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<TaskEntity> criteriaQuery = criteriaBuilder.createQuery(TaskEntity.class);
        Root<TaskEntity> from = criteriaQuery.from(TaskEntity.class);
        Predicate where = criteriaBuilder.equal(from.get("implementer"), implementer);
        return getTaskDtos( criteriaQuery, from, where);
    }


    private List<TaskDto> getTaskDtos( CriteriaQuery<TaskEntity> criteriaQuery, Root<TaskEntity> from, Predicate where) {
        List<TaskDto> result = new ArrayList<>();
        CriteriaQuery<TaskEntity> select = criteriaQuery.select(from).where(where);
        TypedQuery<TaskEntity> typedQuery = entityManager.createQuery(select);

        List<TaskEntity> taskEntities = typedQuery.getResultList();

        for (TaskEntity taskEntity: taskEntities) {
            TaskDto taskDto = new TaskDto();
            BeanUtils.copyProperties(taskEntity, taskDto, "comments");
            result.add(taskDto);
        }
        return result;
    }


    public List<TaskDto> tasksFrom(final String author) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<TaskEntity> criteriaQuery = criteriaBuilder.createQuery(TaskEntity.class);
        Root<TaskEntity> from = criteriaQuery.from(TaskEntity.class);
        Predicate where = criteriaBuilder.equal(from.get("author"), author);
        return getTaskDtos(criteriaQuery, from, where);
    }

    public List<TaskDto> tasksIn(final int project) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<TaskEntity> criteriaQuery = criteriaBuilder.createQuery(TaskEntity.class);
        Root<TaskEntity> from = criteriaQuery.from(TaskEntity.class);
        Predicate where = criteriaBuilder.equal(from.get("project"), project);
        return getTaskDtos( criteriaQuery, from, where);
    }

    public List<TaskDto> filter(final int project, final String author, final String implementer) {
        List<TaskDto> result = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<TaskEntity> cq = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> from = cq.from(TaskEntity.class);
        CriteriaQuery<TaskEntity> select = cq.select(from);
        List<Predicate> predicateList = new ArrayList<>();
        if (project > 0) {
            predicateList.add(cb.equal(from.get("project"),project));
        }
        if (!StringUtils.isEmpty(author) && this.checkEmail(author)) {
            predicateList.add(cb.equal(from.get("author"),author));
        }
        if (!StringUtils.isEmpty(implementer) && this.checkEmail(implementer)) {
            predicateList.add(cb.equal(from.get("implementer"),implementer));
        }
        Predicate[] restrictions = new Predicate[predicateList.size()];
        select.where(predicateList.toArray(restrictions));
        TypedQuery<TaskEntity> typedQuery = entityManager.createQuery(select);

        List<TaskEntity> taskEntities = typedQuery.getResultList();
        for (TaskEntity taskEntity : taskEntities) {
            TaskDto taskDto = new TaskDto();
            BeanUtils.copyProperties(taskEntity, taskDto, "comments");
            result.add(taskDto);
        }
        return result;
    }

    private boolean checkEmail(final String email) {
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.isValid(email, null)) {
            return false;
        }
        return true;
    }
}
