package boost.brain.course.projects.model;

import boost.brain.course.common.projects.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

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

    @ElementCollection
    private Set<String> participatingUsers;

    @ElementCollection
    private Set<String> waitingUsers;

}
