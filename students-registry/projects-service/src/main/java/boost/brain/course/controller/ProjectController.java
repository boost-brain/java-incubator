package boost.brain.course.controller;

import boost.brain.course.controller.exceptions.BadRequestException;
import boost.brain.course.controller.exceptions.NotFoundException;
import boost.brain.course.common.projects.ProjectDto;
import boost.brain.course.model.ProjectMapper;
import boost.brain.course.repository.ProjectRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@Controller
public class ProjectController {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @ResponseBody
    @PostMapping(value = "/createProject")
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        if (StringUtils.isEmpty(projectDto.getProjectName()) ||
                StringUtils.isEmpty(projectDto.getDescription()) ||
                StringUtils.isEmpty(projectDto.getProjectUrl())) {
            throw new BadRequestException();
        }
        ProjectDto result = projectMapper.toProjectDto(projectRepository.save(projectMapper.toProject(projectDto)));
        if (result == null) {
            throw new BadRequestException();
        }
        return result;
    }

    @ResponseBody
    @GetMapping(value="/findById/{projectId}")
    public ProjectDto findById(@PathVariable int projectId) {
        if (projectId < 1) {
            throw new BadRequestException();
        }
        log.info("inside findById(projectId) method; id: " + projectId);
        ProjectDto result = projectMapper.toProjectDto(projectRepository.findByProjectId(projectId));
        if (result == null) {
            log.severe("not found project");
            throw new NotFoundException();
        }
        log.info("found project: " + result.toString());
        return result;
    }

    @ResponseBody
    @GetMapping(value="/countProjects")
    public int countProjects(){
        int result = projectRepository.countAllBy();
        log.info("method: countProjects(); count: "+ result);
        return result;
    }

    @ResponseBody
    @DeleteMapping("/delete/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable int projectId) {
        log.info("deleting project with id: "+ projectId);
        if (projectId < 1) {
            throw new BadRequestException();
        }
        if (projectRepository.deleteProjectByProjectId(projectId) == 1) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            log.severe("not found project");
            throw new NotFoundException();
        }
    }

    @ResponseBody
    @GetMapping(value="/watch/{page}/{size}")
    public Page<ProjectDto> watch(@PathVariable int page, @PathVariable int size) {
        //!!!Костыль
        page--;
        if((page < 0) || (size < 1)) {
            throw new BadRequestException();
        }
        Pageable firstPageWithTwoElements = PageRequest.of(page,size);
        Page<ProjectDto> result = projectMapper.toProjectDtoPage(projectRepository.findAll(firstPageWithTwoElements));
        if (result == null) {
            throw  new NotFoundException();
        }
        return result;
    }

    @ResponseBody
    @PatchMapping(value="/update")
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody ProjectDto projectDto) {
        log.info("method: update");
        if (projectDto.getProjectId() < 1 ||
                StringUtils.isEmpty(projectDto.getProjectName()) ||
                StringUtils.isEmpty(projectDto.getDescription()) ||
                StringUtils.isEmpty(projectDto.getProjectUrl())) {
            throw new BadRequestException();
        }
        if (projectRepository.update(
                projectDto.getProjectUrl(),
                projectDto.getDescription(),
                projectDto.getProjectName(),
                projectDto.getProjectId()) == 1) {
            log.info("Success");
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @ResponseBody
    @PostMapping("/projects-for-ids")
    public List<ProjectDto> projectsForIds(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BadRequestException();
        }
        List<ProjectDto> result =  projectMapper.toProjectDtos(projectRepository.findAllByProjectIdIn(ids));
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/projects-all")
    public List<ProjectDto> allProject() {
        List<ProjectDto> result =  projectMapper.toProjectDtos(projectRepository.findAll());
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/check-if-exists/{projectId}")
    public boolean checkIfExists(@PathVariable int projectId) {
        if (projectId < 1) {
            throw new BadRequestException();
        }
        return projectRepository.existsByProjectId(projectId);
    }

}
