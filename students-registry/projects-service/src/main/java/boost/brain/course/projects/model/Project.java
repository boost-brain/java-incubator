package boost.brain.course.projects.model;

import boost.brain.course.common.projects.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name="project")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;
    private String projectUrl;
    private String description;
    private String projectName;
    private ProjectStatus status;
    @Email
    private String author;

}
