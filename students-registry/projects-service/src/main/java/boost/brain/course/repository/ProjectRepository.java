package boost.brain.course.repository;

import boost.brain.course.model.Project;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project,Integer> {
    @Override
    <S extends Project>S save(S s);

    Project findByProjectId(int projectId);

    int deleteProjectByProjectId(int projectId);

    int countAllBy();

    @Modifying
    @Query("update Project u set u.projectUrl=?1, u.description=?2, u.projectName=?3 where u.projectId=?4")
    int update(String Url, String desc, String Name, int id);

    List<Project> findAll();

    List<Project> findAllByProjectIdIn(List<Integer> list);

    boolean existsByProjectId(int projectId);

}
