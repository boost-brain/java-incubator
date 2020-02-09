package boost.brain.course.common.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegDto {

    private String email;
    private String gitHubId;
    private String name;
    private int hours;
    private String password;
}
