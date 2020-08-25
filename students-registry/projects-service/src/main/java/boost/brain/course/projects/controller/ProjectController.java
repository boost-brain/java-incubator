package boost.brain.course.projects.controller;

import boost.brain.course.common.projects.ProjectDto;
import boost.brain.course.projects.Constants;
import boost.brain.course.projects.controller.exceptions.BadRequestException;
import boost.brain.course.projects.controller.exceptions.NotFoundException;
import boost.brain.course.projects.model.ProjectMapper;
import boost.brain.course.projects.repository.ProjectRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping(Constants.PROJECTS_CONTROLLER_PREFIX)
public class ProjectController {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @ResponseBody
    @PostMapping(Constants.CREATE_PREFIX)
    public ProjectDto create(@RequestBody ProjectDto projectDto) {
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
    @GetMapping(Constants.READ_PREFIX + "/{id}")
    public ProjectDto read(@PathVariable int id) {
        if (id < 1) {
            throw new BadRequestException();
        }
        log.info("inside findById(projectId) method; id: " + id);
        ProjectDto result = projectMapper.toProjectDto(projectRepository.findByProjectId(id));
        if (result == null) {
            log.severe("not found project");
            throw new NotFoundException();
        }
        log.info("found project: " + result.toString());
        return result;
    }

    @ResponseBody
    @GetMapping(Constants.COUNT_PREFIX)
    public int count(){
        int result = projectRepository.countAllBy();
        log.info("method: countProjects(); count: "+ result);
        return result;
    }

    @ResponseBody
    @DeleteMapping(Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable int id) {
        log.info("deleting project with id: "+ id);
        if (id < 1) {
            throw new BadRequestException();
        }
        if (projectRepository.deleteProjectByProjectId(id) == 1) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            log.severe("not found project");
            throw new NotFoundException();
        }
    }

    @ResponseBody
    @GetMapping(Constants.PAGE_PREFIX + "/{page}/{size}")
    public Page<ProjectDto> page(
            @PathVariable int page,
            @PathVariable int size) {
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
    @PatchMapping(Constants.UPDATE_PREFIX)
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
    @PostMapping(Constants.FOR_IDS_PREFIX)
    public List<ProjectDto> forIds(@RequestBody List<Integer> ids) {
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
    @GetMapping(Constants.ALL_PREFIX)
    public List<ProjectDto> all() {
        List<ProjectDto> result =  projectMapper.toProjectDtos(projectRepository.findAll());
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @ResponseBody
    @GetMapping(Constants.IF_EXISTS_PREFIX + "/{id}")
    public boolean ifExists(@PathVariable int id) {
        if (id < 1) {
            throw new BadRequestException();
        }
        return projectRepository.existsByProjectId(id);
    }

}
