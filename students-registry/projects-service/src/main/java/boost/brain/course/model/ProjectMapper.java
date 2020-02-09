package boost.brain.course.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectMapper {

    public ProjectDTO toProjectDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(project.getProjectId());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setProjectUrl(project.getProjectUrl());
        return projectDTO;
    }

    public Project toProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setDescription(projectDTO.getDescription());
        project.setProjectId(projectDTO.getProjectId());
        project.setProjectName(projectDTO.getProjectName());
        project.setProjectUrl(projectDTO.getProjectUrl());
        return project;
    }
    public List<ProjectDTO> toProjectDtos(List<Project> projects) {
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for (Project project : projects){
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setProjectId(project.getProjectId());
            projectDTO.setDescription(project.getDescription());
            projectDTO.setProjectName(project.getProjectName());
            projectDTO.setProjectUrl(project.getProjectUrl());
            projectDTOS.add(projectDTO);
        }
        return projectDTOS;
    }
    public Page<ProjectDTO> toProjectDtoPage(Page<Project> projects) {
        Page<ProjectDTO> projectDTOS = projects.map(this::toProjectDto);
        return projectDTOS;
    }
}
