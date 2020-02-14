package boost.brain.course.dto;

import boost.brain.course.common.users.UserStatus;
import lombok.Data;
import lombok.extern.java.Log;


/**
 * Класс, аналог UserDto с изменённой датой создания и обновления.
 * В UserDto хранятся в long, здесь в String.
 */
@Log
@Data
public class UserDtoWithNormalDate {
    // email студента
    private String email;
    // Имя пользователя на сайте github
    private String gitHubId;
    // Желаемое имя
    private String name;
    // Возможное количество часов в неделю
    private int hours;
    // Дата создания аккаунта
    private String createDate;
    // Дата последнего редактирования
    private String updateDate;
    // Статус занятости в проектах
    private UserStatus status;
}
