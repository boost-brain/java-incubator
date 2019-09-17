package boost.brain.course.frontend.register.model;

import lombok.Data;

import javax.inject.Named;

@Named
@Data
public class User {

    private String name;
    private String email;
    private String password;
    private String gitHubId;
    private int hours;

    public User(){}

    public User(String name, String email, String password, String gitHubId, int hours) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gitHubId = gitHubId;
        this.hours = hours;
    }
}
