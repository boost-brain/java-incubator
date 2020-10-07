package boost.brain.course.projects.repository;

import boost.brain.course.common.projects.ProjectStatus;
import boost.brain.course.projects.model.Project;
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
    @Query("update Project u " +
            "set u.projectUrl=?1, u.description=?2, u.projectName=?3, u.status=?4, u.author=?5 " +
            "where u.projectId=?6 AND u.author=?5")
    int update(String Url, String desc, String Name, ProjectStatus status, String author, int id);

    List<Project> findAll();

    List<Project> findAllByProjectIdIn(List<Integer> list);

    boolean existsByProjectId(int projectId);

}
