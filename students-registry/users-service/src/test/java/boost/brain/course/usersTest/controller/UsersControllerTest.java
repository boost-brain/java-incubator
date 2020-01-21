package boost.brain.course.usersTest.controller;

import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;
import boost.brain.course.users.controller.UsersController;
import boost.brain.course.users.repository.UsersRepository;
import boost.brain.course.usersTest.utils.UserDtoProviderUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Oleg Pavlyukov
 * on 21.01.2020
 * cpabox777@gmail.com
 */
@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @InjectMocks
    UsersController usersController;

    @Mock
    UsersRepository usersRepository;

    private UserDto userDto;
    private List<UserDto> userDtoList;

    @BeforeEach
    void setUp() {
        userDto = UserDtoProviderUtil.generateEntity();
        userDtoList = UserDtoProviderUtil.generateEntityList(5);
    }

    @AfterEach
    void tearDown() {
        userDto = null;
        userDtoList = null;
    }

    @Test
    void create() {
        // копируем все поля в новый объект, чтобы удостовериться, что контроллер их изменит
        UserDto controlUserDto = new UserDto();
        BeanUtils.copyProperties(userDto, controlUserDto);

        when(usersRepository.create(userDto)).thenReturn(userDto);

        UserDto resultUserDto = usersController.create(userDto);

        assertNotNull(resultUserDto);
        assertNotEquals(controlUserDto.getCreateDate(), resultUserDto.getCreateDate());
        assertNotEquals(controlUserDto.getUpdateDate(), resultUserDto.getUpdateDate());
        assertNotEquals(controlUserDto.getStatus(), resultUserDto.getStatus());

        // проверяем, что usersRepository.create(userDto) вызывался один раз
        verify(usersRepository, times(1)).create(any(UserDto.class));
    }

    @Test
    void read() {
        when(usersRepository.read(userDto.getEmail())).thenReturn(userDto);

        UserDto resultUserDto = usersController.read(userDto.getEmail());

        assertNotNull(resultUserDto);
        assertEquals(userDto, resultUserDto);
        verify(usersRepository, times(1)).read(anyString());
    }

    @Test
    void update() {
        String reasonPhraseOK = HttpStatus.OK.getReasonPhrase();

        when(usersRepository.update(userDto)).thenReturn(true);

        String responseString = usersController.update(userDto);

        assertNotNull(responseString);
        assertEquals(reasonPhraseOK, responseString);
        verify(usersRepository, times(1)).update(any(UserDto.class));
    }

    @Test
    void updatePut() {
        String reasonPhraseOK = HttpStatus.OK.getReasonPhrase();

        when(usersRepository.update(userDto)).thenReturn(true);

        String responseString = usersController.updatePut(userDto);

        assertNotNull(responseString);
        assertEquals(reasonPhraseOK, responseString);
        verify(usersRepository, times(1)).update(any(UserDto.class));
    }

    @Test
    void delete() {
        String reasonPhraseOK = HttpStatus.OK.getReasonPhrase();

        when(usersRepository.delete(userDto.getEmail())).thenReturn(true);

        String responseString = usersController.delete(userDto.getEmail());

        assertNotNull(responseString);
        assertEquals(reasonPhraseOK, responseString);
        verify(usersRepository, times(1)).delete(anyString());
    }

    @Test
    void count() {
        Long count = (long) userDtoList.size();
        when(usersRepository.count()).thenReturn(count);

        Long responseCount = usersController.count();

        assertNotNull(responseCount);
        assertEquals(count, responseCount);
        verify(usersRepository, times(1)).count();
    }

    @Test
    void page() {
        int page = 10;
        int size = 15;
        userDtoList = UserDtoProviderUtil.generateEntityList(size);

        when(usersRepository.getPage(page, size)).thenReturn(userDtoList);

        List<UserDto> resultUserDtoList = usersController.page(page, size);

        assertNotNull(resultUserDtoList);
        assertEquals(userDtoList, resultUserDtoList);
        verify(usersRepository, times(1)).getPage(anyInt(), anyInt());
    }

    @Test
    void usersForEmails() {
        List<String> emailList = userDtoList.stream()
                                            .map(UserDto::getEmail)
                                            .collect(Collectors.toList());

        when(usersRepository.usersForEmails(emailList)).thenReturn(userDtoList);

        List<UserDto> resultUserDtoList = usersController.usersForEmails(emailList);

        assertNotNull(resultUserDtoList);
        assertEquals(userDtoList, resultUserDtoList);
        verify(usersRepository, times(1)).usersForEmails(anyList());
    }

    @Test
    void allUsers() {
        when(usersRepository.allUsers()).thenReturn(userDtoList);

        List<UserDto> resultUserDtoList = usersController.allUsers();

        assertNotNull(resultUserDtoList);
        assertEquals(userDtoList, resultUserDtoList);
        verify(usersRepository, times(1)).allUsers();
    }

    @Test
    void checkIfExists() {
        when(usersRepository.checkIfExists(userDto.getEmail())).thenReturn(true);
        boolean responseValue = usersController.checkIfExists(userDto.getEmail());

        assertTrue(responseValue);
        verify(usersRepository, times(1)).checkIfExists(userDto.getEmail());
    }

    @Test
    void updateStatus() {
        String reasonPhraseOK = HttpStatus.OK.getReasonPhrase();
        UserStatus requestStatus = UserStatus.TEMPORARILY_INACTIVE;

        when(usersRepository.read(userDto.getEmail())).thenReturn(userDto);
        when(usersRepository.updateStatus(userDto)).thenReturn(true);

        String resultReasonPhrase = usersController.updateStatus(userDto.getEmail(), requestStatus);

        assertNotNull(resultReasonPhrase);
        assertEquals(reasonPhraseOK, resultReasonPhrase);
        verify(usersRepository, times(1)).read(anyString());
        verify(usersRepository, times(1)).updateStatus(any(UserDto.class));
    }

    @Test
    void updateStatusesForEmails() {
        String reasonPhraseOK = HttpStatus.OK.getReasonPhrase();
        Map<String, UserStatus> requestMap = userDtoList.stream()
                                                        .collect(Collectors.toMap(UserDto::getEmail, UserDto::getStatus));
        when(usersRepository.updateStatusesForEmails(requestMap)).thenReturn(true);

        String responseString = usersController.updateStatusesForEmails(requestMap);

        assertNotNull(responseString);
        assertEquals(reasonPhraseOK, responseString);
        verify(usersRepository, times(1)).updateStatusesForEmails(anyMap());
    }
}