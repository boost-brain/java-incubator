package boost.brain.course.common.users;

import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String gitHabId;
    private String name;
    private int hours;
    private long createDate;
    private long updateDate;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGitHabId() {
        return gitHabId;
    }

    public void setGitHabId(String gitHabId) {
        this.gitHabId = gitHabId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
