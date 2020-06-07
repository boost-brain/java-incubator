package boost.brain.course.common.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Credentials {
    @ApiModelProperty(notes = "Login для входа",example = "AdminIvanov",position=1)
    private String login;
    @ApiModelProperty(notes = "Пароль для входа, хранится в зашифрованном виде (MD5).",example = "1BC29B36F623BA82AAF6724FD3B16718",position=2)
    private String password;
}
