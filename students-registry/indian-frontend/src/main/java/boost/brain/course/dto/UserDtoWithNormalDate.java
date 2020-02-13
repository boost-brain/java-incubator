package boost.brain.course.dto;

import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;


/**
 * Класс, аналог UserDto с изменённой датой создания и обновления.
 * В UserDto хранятся в long, здесь в LocalDate.
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
    public static UserDtoWithNormalDate UserDtoToUserDtoWithNormalDate(Object obj) {
        if (obj.getClass().isInstance(UserDto.class)) {
            UserDto userDto = (UserDto) obj;
            UserDtoWithNormalDate userDtoWithNormalDate = new UserDtoWithNormalDate();
            BeanUtils.copyProperties(userDto, userDtoWithNormalDate, "createDate", "updateDate");
            log.info(userDtoWithNormalDate.toString());
            userDtoWithNormalDate.createDate = dateFromLong(userDto.getCreateDate());
            userDtoWithNormalDate.updateDate = dateFromLong(userDto.getUpdateDate());
            return userDtoWithNormalDate;
        }
        return null;
    }

    /*
    Преобразует дату из long в LocalDate
     */
    private static String dateFromLong(long createDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return new Timestamp(createDate).toLocalDateTime().format(DateTimeFormatter.ofPattern("DD MM YYYY"));
    }
}
