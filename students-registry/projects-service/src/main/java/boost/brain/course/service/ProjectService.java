package boost.brain.course.service;

import boost.brain.course.model.Project;
import boost.brain.course.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void createProject(String JSON) throws IOException {

        ObjectMapper objectMapper=new ObjectMapper();
        return;
    }


}
