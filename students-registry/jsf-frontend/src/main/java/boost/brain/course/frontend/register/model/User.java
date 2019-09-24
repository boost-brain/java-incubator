package boost.brain.course.frontend.register.model;

import lombok.Data;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@Data
public class User {

    private String name;
    private String email;
    private String password;
    private String gitHabId;
    private int hours;

    public User(){}

    public User(String name, String email, String password, String gitHabId, int hours) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gitHabId = gitHabId;
        this.hours = hours;
    }
}
