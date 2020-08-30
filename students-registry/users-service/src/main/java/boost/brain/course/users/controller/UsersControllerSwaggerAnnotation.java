package boost.brain.course.users.controller;

import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;
import boost.brain.course.users.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api
public interface UsersControllerSwaggerAnnotation {

    @ApiOperation(value = "Добавление нового пользователя. Обязательно должны быть заполнены поля слздаваемого объекта.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создание нового пользователя."),
            @ApiResponse(code = 400, message = "Не заполнены обязательные поля у нового пользователя."),
            @ApiResponse(code = 409, message = "Не удалось записать нового пользователя.")
    })
    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UserDto create(@RequestBody UserDto userDto);

    @ApiOperation(value = "Получения пользователя по его e-mail.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Получен пользователь."),
            @ApiResponse(code = 404, message = "Пользователь не найден."),
    })
    @GetMapping(path = Constants.READ_PREFIX + "/{email}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UserDto read(@PathVariable String email);

    @ApiOperation(value = "Обновление данных пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновление данных пользователя."),
            @ApiResponse(code = 400, message = "Не заполнены обязательные поля у пользователя."),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    String update(@RequestBody UserDto userDto);

    @ApiOperation(value = "Обновление данных пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновление данных пользователя."),
            @ApiResponse(code = 400, message = "Не заполнены обязательные поля у пользователя."),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    @PutMapping(path = Constants.PUT_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    String updatePut(@RequestBody UserDto userDto);

    @ApiOperation(value = "Удаление пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удален."),
            @ApiResponse(code = 400, message = "Неверный e-mail пользователя."),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{email}")
    @ResponseStatus(HttpStatus.OK)
    String delete(@PathVariable String email);

    @ApiOperation(value = "Подсчет количества пользователей.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Количество пользователей."),
    })
    @GetMapping(path = Constants.COUNT_PREFIX,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    long count();

    @ApiOperation(value = "Получение списка пользователей с пагинацией.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей."),
            @ApiResponse(code = 400, message = "Не верные значения номера страницы и ее размеры."),
            @ApiResponse(code = 409, message = "Данные запрашиваемой страницы пользователей не найдены.")
    })
    @GetMapping(path = Constants.PAGE_PREFIX + "/{page}/{size}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<UserDto> page(@PathVariable int page, @PathVariable int size);

    @ApiOperation(value = "Получение списка пользователей по списку e-mail.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей."),
            @ApiResponse(code = 400, message = "Не верные значения коллекции e-mail, возможно коллекция пуста."),
            @ApiResponse(code = 409, message = "Коллекция пользователей с такими e-mail не найдена.")
    })
    @PostMapping(path = Constants.USERS_FOR_EMAILS_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<UserDto> usersForEmails(@RequestBody List<String> emails);

    @ApiOperation(value = "Получение списка всех пользователей.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей."),
            @ApiResponse(code = 409, message = "Не удалось получить список всех пользователей.")
    })
    @GetMapping(path = Constants.USERS_ALL_PREFIX,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<UserDto> allUsers();

    @ApiOperation(value = "Проверка существования пользователя с таким e-mail.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Резултат проверки."),
            @ApiResponse(code = 400, message = "Не верный e-mail.")
    })
    @GetMapping(path = Constants.CHECK_IF_EXISTS_PREFIX + "/{email}")
    boolean checkIfExists(@PathVariable String email);

    @ApiOperation(value = "Изменение статуса у пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Статус изменен."),
            @ApiResponse(code = 400, message = "Пустое значение e-mail или статуса."),
            @ApiResponse(code = 404, message = "Пользователь с таким e-mail не найден."),
            @ApiResponse(code = 409, message = "Не удалось изменить статус.")
    })
    @PostMapping(path = Constants.UPDATE_STATUS_PREFIX + "/{email}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    String updateStatus(@PathVariable String email,
                        @RequestBody UserStatus status);

    @ApiOperation(value = "Изменение статуса у группы пользователей.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Статус изменен."),
            @ApiResponse(code = 400, message = "Пустое значение e-mail или статуса."),
            @ApiResponse(code = 409, message = "Не удалось изменить статус.")
    })
    @PostMapping(path = Constants.UPDATE_STATUSES_FOR_EMAILS_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    String updateStatusesForEmails(@RequestBody Map<String, UserStatus> emailsWithStatusesMap);
}
