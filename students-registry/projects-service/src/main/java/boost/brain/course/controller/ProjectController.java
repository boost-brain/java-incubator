package boost.brain.course.controller;

import boost.brain.course.model.Project;
import boost.brain.course.repository.ProjectRepository;
import boost.brain.course.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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

    @ResponseBody
    @RequestMapping(value="/findById",method = RequestMethod.GET)
    public Project findById(@RequestBody Project id) throws NoSuchElementException{
        System.out.println(id.getProjectId());
       Project project = projectRepository.findById(id.getProjectId()).get();
        System.out.println(project.toString());
        return project;
    }

    @ResponseBody
    @RequestMapping(value="/countProjects", method = RequestMethod.GET)
    public int countProjects(){
        System.out.println("count: "+projectRepository.countAllBy());
        return projectRepository.countAllBy();
    }

    @RequestMapping(value="/delete",method = RequestMethod.DELETE)
    public void deleteProject(@RequestBody Project project){
        System.out.println("deleting:"+project.toString());
        projectRepository.delete(project);
        return;
    }

    @ResponseBody
    @Transactional
    @RequestMapping(value="/deleteById", method = RequestMethod.DELETE)
    public  String deleteById(@RequestBody Project project){
        String result ="deleting project with id: "+project.getProjectId();
        projectRepository.deleteProjectByProjectId(project.getProjectId());
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/watch", method = RequestMethod.GET)
    public Page<Project> watch(){
        return projectService.getPage();
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public Project update(@RequestBody Project project){
        System.out.println("Updating project");
        System.out.println("Before:"+projectRepository.findById(project.getProjectId()).get().toString());
        System.out.println("After:"+project.toString());
        projectRepository.update(project.getProjectUrl(),project.getDescription(),project.getProjectName(),project.getProjectId());
        System.out.println("Success");
        return project;
    }
}
