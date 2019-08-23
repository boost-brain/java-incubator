package boost.brain.course.controller;

import boost.brain.course.model.ProjectDTO;
import boost.brain.course.model.ProjectMapper;
import boost.brain.course.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

   // ProjectController(){projectMapper=new ProjectMapper();}

//    ApplicationContext context = new AnnotationConfigApplicationContext(SwaggerConfig.class);
  //  ProjectMapper projectMapper=context.getBean(ProjectMapper.class);
    ProjectMapper projectMapper=new ProjectMapper();

    @ResponseBody
    @RequestMapping(value = "/createProject")
    public ProjectDTO createProject(@RequestBody ProjectDTO project){
        projectRepository.save(projectMapper.toProject(project));
        return project;
    }
    @ResponseBody
    @RequestMapping(value="/findById",method = RequestMethod.GET)
    public ProjectDTO findById(@RequestBody ProjectDTO id) throws NoSuchElementException{
        System.out.println(id.getProjectId());
       //Project project = projectRepository.findById(id.getProjectId()).get();
        System.out.println(projectRepository.findById(projectMapper.toProject(id).getProjectId()));
        ProjectDTO project = projectMapper.toProjectDto(projectRepository.findById(projectMapper.toProject(id).getProjectId()).get());
        System.out.println(project.toString());
        return project;
    }
    @ResponseBody
    @RequestMapping(value="/countProjects", method = RequestMethod.GET)
    public int countProjects(){
        System.out.println("count: "+projectRepository.countAllBy());
        return projectRepository.countAllBy();
    }
    @ResponseBody
    @RequestMapping(value="/deleteById", method = RequestMethod.DELETE)
    public  String deleteById(@RequestBody ProjectDTO project){
        String result ="deleting project with id: "+project.getProjectId();
        projectRepository.deleteProjectByProjectId(projectMapper.toProject(project).getProjectId());
        return result;
    }
    @ResponseBody
    @RequestMapping(value="/watch", method = RequestMethod.GET)
    public Page<ProjectDTO> watch(){
        Pageable firstPageWithTwoElements = PageRequest.of(0,2);
        Page<ProjectDTO> page=projectMapper.toProjectDtoPage(projectRepository.findAll(firstPageWithTwoElements));
        System.out.println(page);
        return projectMapper.toProjectDtoPage(projectRepository.findAll(firstPageWithTwoElements));
    }
    @ResponseBody
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ProjectDTO update(@RequestBody ProjectDTO project){
        System.out.println("Updating project");
        System.out.println("Before:"+projectRepository.findById(projectMapper.toProject(project).getProjectId()).get().toString());
        System.out.println("After:"+project.toString());
        projectRepository.update(project.getProjectUrl(),project.getDescription(),project.getProjectName(),project.getProjectId());
        System.out.println("Success");
        return project;
    }
}
