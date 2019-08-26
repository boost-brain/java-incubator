package boost.brain.course.tasks.repository;

import boost.brain.course.tasks.controller.dto.CommentDto;
import boost.brain.course.tasks.repository.entities.CommentEntity;
import boost.brain.course.tasks.repository.entities.TaskEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
public class CommentRepository {

    private final EntityManager entityManager;

    @Autowired
    public CommentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CommentDto create(final CommentDto commentDto) {
        if (commentDto == null) {
            return null;
        }
        TaskEntity taskEntity = entityManager.find(TaskEntity.class, commentDto.getTaskId());
        if (taskEntity == null) {
            return null;
        }
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentDto, commentEntity, "id");
        commentEntity.setTaskId(taskEntity);
        entityManager.persist(commentEntity);

        CommentDto result = new CommentDto();
        BeanUtils.copyProperties(commentEntity, result, "taskId");
        result.setTaskId(commentEntity.getTaskId().getId());
        return result;
    }

    public CommentDto read(final long id) {
        CommentEntity commentEntity = entityManager.find(CommentEntity.class, id);
        if (commentEntity == null) {
            return null;
        }
        CommentDto result = new CommentDto();
        BeanUtils.copyProperties(commentEntity, result, "taskId");
        result.setTaskId(commentEntity.getTaskId().getId());
        return result;
    }

    public boolean delete(final long id) {
        CommentEntity commentEntity = entityManager.find(CommentEntity.class, id);
        if (commentEntity == null) {
            return false;
        }
        entityManager.remove(commentEntity);
        return true;
    }

    public boolean update(final CommentDto commentDto) {
        if (commentDto == null) {
            return false;
        }
        CommentEntity commentEntity = entityManager.find(CommentEntity.class, commentDto.getId());
        if (commentEntity == null) {
            return false;
        }
        BeanUtils.copyProperties(commentDto, commentEntity, "createDate", "author","taskId");
        entityManager.merge(commentEntity);
        return true;
    }

    public long count() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(CommentEntity.class)));
        Long result = entityManager.createQuery(countQuery).getSingleResult();
        return result;
    }

    public List<CommentDto> commentsFor(final long taskId) {
        TaskEntity taskEntity = entityManager.find(TaskEntity.class, taskId);
        if (taskEntity == null) {
            return null;
        }
        List<CommentDto> result = new ArrayList<>();
        for (CommentEntity commentEntity: taskEntity.getComments()) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(commentEntity, commentDto, "taskId");
            commentDto.setTaskId(commentEntity.getTaskId().getId());
            result.add(commentDto);
        }
        return result;
    }

    public List<CommentDto> filter(final long taskId, final int author) {
        List<CommentDto> result = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<CommentEntity> cq = cb.createQuery(CommentEntity.class);
        Root<CommentEntity> from = cq.from(CommentEntity.class);
        CriteriaQuery<CommentEntity> select = cq.select(from);
        List<Predicate> predicateList = new ArrayList<>();
        if (taskId > 0) {
            predicateList.add(cb.equal(from.get("taskId"),taskId));
        }
        if (author > 0) {
            predicateList.add(cb.equal(from.get("author"),author));
        }
        Predicate[] restrictions = new Predicate[predicateList.size()];
        select.where(predicateList.toArray(restrictions));
        TypedQuery<CommentEntity> typedQuery = entityManager.createQuery(select);

        List<CommentEntity> commentEntities = typedQuery.getResultList();
        for (CommentEntity commentEntity : commentEntities) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(commentEntity, commentDto, "taskId");
            commentDto.setTaskId(commentEntity.getTaskId().getId());
            result.add(commentDto);
        }
        return result;
    }
}
