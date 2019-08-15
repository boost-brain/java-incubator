package boost.brain.course.repository;

import boost.brain.course.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {

    @Override
    <S extends Project>S save(S s);

    public List<Project> getAllByProjectId(int projectId);
    public void deleteProjectByProjectId(int projectId);

}
