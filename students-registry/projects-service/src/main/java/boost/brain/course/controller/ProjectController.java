package boost.brain.course.controller;

import boost.brain.course.controller.exceptions.NotFoundException;
import boost.brain.course.model.Project;
import boost.brain.course.model.ProjectDTO;
import boost.brain.course.model.ProjectMapper;
import boost.brain.course.repository.ProjectRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Log
@Controller
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

   // ProjectController(){projectMapper=new ProjectMapper();}

//    ApplicationContext context = new AnnotationConfigApplicationContext(SwaggerConfig.class);
  //  ProjectMapper projectMapper=context.getBean(ProjectMapper.class);
    ProjectMapper projectMapper=new ProjectMapper();

    @ResponseBody
    @PostMapping(value = "/createProject")
    public ProjectDTO createProject(@RequestBody ProjectDTO project){
        projectRepository.save(projectMapper.toProject(project));
        return project;
    }
    @ResponseBody
    @GetMapping(value="/findById")
    public ProjectDTO findById(@RequestBody ProjectDTO id) throws NoSuchElementException{
        log.info("inside findById(id) method; id:"+id.getProjectId());
        log.info("found: "+projectRepository.findById(projectMapper.toProject(id).getProjectId()));
        //System.out.println(id.getProjectId());
       //Project project = projectRepository.findById(id.getProjectId()).get();
       // System.out.println(projectRepository.findById(projectMapper.toProject(id).getProjectId()));
        ProjectDTO project = projectMapper.toProjectDto(projectRepository.findById(projectMapper.toProject(id).getProjectId()).get());
        log.info("project: "+project.toString());
       // System.out.println(project.toString());
        return project;
    }
    @ResponseBody
    @GetMapping(value="/countProjects")
    public int countProjects(){
        log.info("method: countProjects(); count: "+projectRepository.countAllBy());
        //System.out.println("count: "+projectRepository.countAllBy());
        return projectRepository.countAllBy();
    }
    @ResponseBody
    @DeleteMapping(value="/deleteById")
    public  String deleteById(@RequestBody ProjectDTO project){
        String result ="deleting project with id: "+project.getProjectId();
        projectRepository.deleteProjectByProjectId(projectMapper.toProject(project).getProjectId());
        return result;
    }
    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Project project) {
        projectRepository.delete(project);
    }
    @ResponseBody
    @GetMapping(value="/watch/{page}/{size}")
    public Page<ProjectDTO> watch(@PathVariable int page, @PathVariable int size){
        Pageable firstPageWithTwoElements = PageRequest.of(page,size);
        if(page<0||size <1)throw new NotFoundException();
        Page<ProjectDTO> result=projectMapper.toProjectDtoPage(projectRepository.findAll(firstPageWithTwoElements));
        if (result == null) throw  new NotFoundException();
        return result;
    }
    @ResponseBody
    @PostMapping(value="/update")
    public ProjectDTO update(@RequestBody ProjectDTO project){
        log.info("method: update");
        log.info("Before:"+projectRepository.findById(projectMapper.toProject(project).getProjectId()).get().toString());
        log.info("After:"+project.toString());
        projectRepository.update(project.getProjectUrl(),project.getDescription(),project.getProjectName(),project.getProjectId());
        log.info("Success");
        return project;
    }
    @ResponseBody
    @GetMapping("/all")
    public List<Project> list() {
        return projectRepository.findAll();
    }

    @ResponseBody
    @PostMapping("/projects-for-ids")
    public List<ProjectDTO> projectsForIds(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new NotFoundException();
        }
        List<ProjectDTO> result =  projectMapper.toProjectDtos(projectRepository.findAllByProjectIdIn(ids));
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }
}
