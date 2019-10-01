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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGitHabId() {
        return gitHabId;
    }

    public void setGitHabId(String gitHabId) {
        this.gitHabId = gitHabId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
