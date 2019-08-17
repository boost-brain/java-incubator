package boost.brain.course.service;

import boost.brain.course.model.Project;
import boost.brain.course.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void createProject(String JSON) throws IOException {

        ObjectMapper objectMapper=new ObjectMapper();
        return;
    }
    Pageable firstPageWithTwoElements = PageRequest.of(0,2);
 //   Pageable secondPageWithFiveElments = PageRequest.of(1,5);

    public Page<Project> getPage(){
        Page<Project> allProjects = projectRepository.findAll(firstPageWithTwoElements);
        System.out.println(allProjects);
        return allProjects;
    }

}
