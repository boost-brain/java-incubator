package boost.brain.course.controller;

import boost.brain.course.model.Project;
import boost.brain.course.repository.ProjectRepository;
import boost.brain.course.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectRepository projectRepository;

    @ResponseBody
    @RequestMapping(value = "/createProject")
    public Project createProject(@RequestBody Project project){
        Project result = projectRepository.save(project);
        return result;
    }
}
