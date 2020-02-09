package boost.brain.course.example.repository;

import boost.brain.course.example.controller.dto.ExampleDto;
import boost.brain.course.example.repository.entities.ExampleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
//Для простых репозиториев используйте что то типа SimpleJpaRepository
//но это учебный пример
public class ExampleRepository {

    private final EntityManager entityManager;

    @Autowired
    public ExampleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ExampleDto create(ExampleDto exampleDto) {
        if (exampleDto == null) {
            return null;
        }

        ExampleEntity exampleEntity = new ExampleEntity();
        BeanUtils.copyProperties(exampleDto, exampleEntity, "id");
        entityManager.persist(exampleEntity);

        ExampleDto result = new ExampleDto();
        BeanUtils.copyProperties(exampleEntity, result);

        return result;
    }

    public ExampleDto read(long id) {
        ExampleEntity exampleEntity = entityManager.find(ExampleEntity.class, id);
        if (exampleEntity == null) {
            return null;
        }

        ExampleDto result = new ExampleDto();
        BeanUtils.copyProperties(exampleEntity, result);

        return result;
    }

    public boolean update(ExampleDto exampleDto) {
        if (exampleDto == null) {
            return false;
        }

        ExampleEntity exampleEntity = entityManager.find(ExampleEntity.class, exampleDto.getId());
        if (exampleEntity == null) {
            return false;
        }

        BeanUtils.copyProperties(exampleDto, exampleEntity);
        entityManager.merge(exampleEntity);

        return true;
    }

    public boolean delete(long id) {
        ExampleEntity exampleEntity = entityManager.find(ExampleEntity.class, id);
        if (exampleEntity == null) {
            return false;
        }

        entityManager.remove(exampleEntity);
        return true;
    }

}
