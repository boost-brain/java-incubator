package boost.brain.course.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String email;
    private String gitHabId;
    private String name;
    private int hours;
    private long createDate;
    private long updateDate;
    private String password;
}
