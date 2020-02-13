package boost.brain.course.dto;

import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;


/**
 * Класс, аналог UserDto с изменённой датой создания и обновления.
 * В UserDto хранятся в long, здесь в String.
 */
@Log
@Data
public class UserDtoWithNormalDate {
    private String email;
    private String gitHubId;
    private String name;
    private int hours;
    private String createDate;
    private String updateDate;
    private UserStatus status;

    /**
     * Метод преобразует класс UserDto в UserDtoToUserDtoWithNormalDate
     */
    public static UserDtoWithNormalDate UserDtoToUserDtoWithNormalDate(UserDto userDto) {
        UserDtoWithNormalDate userDtoWithNormalDate = new UserDtoWithNormalDate();
        BeanUtils.copyProperties(userDto, userDtoWithNormalDate, "createDate", "updateDate");
        userDtoWithNormalDate.createDate = dateFromLong(userDto.getCreateDate());
        userDtoWithNormalDate.updateDate = dateFromLong(userDto.getUpdateDate());
        return userDtoWithNormalDate;
    }

    /**
    Преобразует дату из long в String
     */
    private static String dateFromLong(long createDate) {
        return new Timestamp(createDate).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
