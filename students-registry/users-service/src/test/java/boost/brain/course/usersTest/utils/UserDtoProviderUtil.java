package boost.brain.course.usersTest.utils;

import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Oleg Pavlyukov
 * on 21.01.2020
 * cpabox777@gmail.com
 */
public class UserDtoProviderUtil {

    public static UserDto generateEntity(String email,
                           String gitHubId,
                           String name,
                           int hours,
                           long createDate,
                           long updateDate,
                           UserStatus status) {

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setGitHubId(gitHubId);
        userDto.setName(name);
        userDto.setHours(hours);
        userDto.setCreateDate(createDate);
        userDto.setUpdateDate(updateDate);
        userDto.setStatus(status);

        return userDto;
    }
    public static UserDto generateEntity() {

        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setGitHubId("github_id 1");
        userDto.setName("Test user 1");
        userDto.setHours(10);
        userDto.setCreateDate(new Date().getTime());
        userDto.setUpdateDate(new Date().getTime() + 1000000L);
        userDto.setStatus(UserStatus.WAITING_FOR_A_TASK);

        return userDto;
    }

    public static List<UserDto> generateEntityList(int length) {
        return Stream
                .iterate(1, n -> n + 1).limit(length)
                .map(num -> {
                    UserDto userDto = new UserDto();
                    userDto.setEmail(num + "_test@example.com");
                    userDto.setGitHubId("github_id " + num);
                    userDto.setName("Test user " + num);
                    userDto.setHours(10 + num);
                    userDto.setCreateDate(new Date().getTime());
                    userDto.setUpdateDate(new Date().getTime() + 1000000L);
                    userDto.setStatus(UserStatus.WAITING_FOR_A_TASK);
                    return userDto;
                })
                .collect(Collectors.toList());
    }
}
