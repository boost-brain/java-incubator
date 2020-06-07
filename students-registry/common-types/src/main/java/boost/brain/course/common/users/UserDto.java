package boost.brain.course.common.users;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDto {
    @ApiModelProperty(notes = "E-mail пользователя", example = "user@mail.me", position = 1)
    private String email;
    @ApiModelProperty(notes = "Аккаунт пользователя на GitHub", example = "myGitHub", position = 2)
    private String gitHubId;
    @ApiModelProperty(notes = "Имя пользователя", example = "Алёша", position = 3)
    private String name;
    @ApiModelProperty(notes = "Количество часов в неделю, которое пользователь может посветить учебным проектам", example = "4", position = 4)
    private int hours;
    @ApiModelProperty(notes = "Время создания пользователя", example = "2356488", position = 5)
    private long createDate;
    @ApiModelProperty(notes = "Время обновления пользователя", example = "23985218", position = 6)
    private long updateDate;
    @ApiModelProperty(notes = "Статус пользователя, выбирается из enum UserStatus", example = "Занят", position = 7)
    private UserStatus status;
}
