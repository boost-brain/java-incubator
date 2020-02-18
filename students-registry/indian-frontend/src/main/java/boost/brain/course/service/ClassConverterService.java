package boost.brain.course.service;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.register.UserRegDto;
import boost.brain.course.common.tasks.TaskDto;
import boost.brain.course.common.users.UserDto;
import boost.brain.course.dto.TaskDtoWithNormalDate;
import boost.brain.course.dto.UserDtoWithNormalDate;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class ClassConverterService {
    /**
     * Метод преобразует класс UserDto в UserDtoToUserDtoWithNormalDate
     */
    public static UserDtoWithNormalDate getUserDtoToUserDtoWithNormalDate(UserDto userDto) {
        UserDtoWithNormalDate userDtoWithNormalDate = new UserDtoWithNormalDate();
        BeanUtils.copyProperties(userDto, userDtoWithNormalDate, "createDate", "updateDate");
        userDtoWithNormalDate.setCreateDate(dateFromLong(userDto.getCreateDate()));
        userDtoWithNormalDate.setUpdateDate(dateFromLong(userDto.getUpdateDate()));
        return userDtoWithNormalDate;
    }

    /**
     * Делает из userDto объект класса Credentials
     *
     * @param userRegDto объект
     * @return объект
     */
    public static Credentials getCredentialsFromUserReg(UserRegDto userRegDto) {
        Credentials credentials = new Credentials();
        credentials.setLogin(userRegDto.getEmail());
        credentials.setPassword(userRegDto.getPassword());
        return credentials;
    }

    /**
     * Преобразует TaskDto в TaskDtoWithNormalDate
     */
    public static TaskDtoWithNormalDate getTaskDtoToTaskDtoWithNormalDate(TaskDto taskDto) {
        TaskDtoWithNormalDate taskDtoWithNormalDate = new TaskDtoWithNormalDate();
        BeanUtils.copyProperties(taskDto, taskDtoWithNormalDate, "createDate", "updateDate");
        taskDtoWithNormalDate.setCreateDate(dateFromLong(taskDto.getCreateDate()));
        taskDtoWithNormalDate.setUpdateDate(dateFromLong(taskDto.getUpdateDate()));
        return taskDtoWithNormalDate;
    }

    /*
     Преобразует дату из long в String
     */
    private static String dateFromLong(long createDate) {
        return new Timestamp(createDate).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
