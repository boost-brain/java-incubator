package boost.brain.course.common.projects;

import lombok.Data;

@Data
public class ProjectDto {

        private int projectId;
        private String projectUrl;
        private String description;
        private String projectName;
        private ProjectStatus status;

}
