package boost.brain.course.users.controller;

import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api
public interface UserControllerApiMethodSwaggerAnnotationsAble {

    @ApiOperation(value = "Добавление нового пользователя в формате JSON, если поля (кроме id, createDate и updateDate) " +
            "в передаваемом объекте пустые или не проходят валидацию, то отдаётся Runtime исключение " +
            "BadRequestException (HttpStatus.BAD_REQUEST). В случае если полученный объект был корректный, но не получилось его добавить, отдаётся " +
            "Runtime исключение ConflictException (HttpStatus.CONFLICT).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создан UserDto объект.", response = UserDto.class),
            @ApiResponse(code = 409, message = "Поля (кроме id, createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 400, message = "Полученный объект был корректный, но не получилось его добавить.")
    })
    UserDto create(@RequestBody UserDto userDto);


    @ApiOperation(value = "Чтение пользователя в формате JSON по его e-mail, если e-mail не корректный, то отдаётся Runtime исключение NotFoundException " +
            "(HttpStatus.NOT_FOUN). Если пользователь не найден, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Передан  UserDto объект из БД.", response = UserDto.class),
            @ApiResponse(code = 404, message = "Объект  UserDto в БД не найден.")
    })
    UserDto read(@PathVariable String email);


    @ApiOperation(value = "Обновление данных пользователя (UserDto в формате JSON)," +
            " если поля (кроме createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию, " +
            "то отдаётся Runtime исключение BadRequestException (HttpStatus.BAD_REQUEST)." +
            "В случае если полученный объект был корректный, но не получилось его обновить, отдаётся " +
            "Runtime исключение NotFoundException (HttpStatus.NOT_FOUND).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновлен UserDto объект.", response = UserDto.class),
            @ApiResponse(code = 400, message = "Поля (кроме id, createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 404, message = "Объект UserDto в БД не найден."),
    })
    String update(@RequestBody UserDto userDto);

    @ApiOperation(value = "Обновление данных пользователя (UserDto в формате JSON)," +
            " если поля (кроме createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию, " +
            "то отдаётся Runtime исключение BadRequestException (HttpStatus.BAD_REQUEST)." +
            "В случае если полученный объект был корректный, но не получилось его обновить, отдаётся " +
            "Runtime исключение NotFoundException (HttpStatus.NOT_FOUND).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновлен UserDto объект.", response = UserDto.class),
            @ApiResponse(code = 400, message = "Поля (кроме id, createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 404, message = "Объект UserDto в БД не найден."),
    })
    String updatePut(@RequestBody UserDto userDto);

    @ApiOperation(value = "Удаление пользователя (UserDto) по e-mail, если e-mail не корректный, то отдаётся Runtime исключение BadRequestException " +
            "(HttpStatus.BAD_REQUEST). Если пользователь не найдено, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Удален UserDto объект.", response = String.class),
            @ApiResponse(code = 400, message = "Запрашиваемый e-mail пустой или не проходит валидацию."),
            @ApiResponse(code = 404, message = "Объект UserDto c запрашиваемым e-mail в БД не найден."),
    })
    String delete(@PathVariable final String email);

    @ApiOperation(value = "Получение количества всех хранимых пользователей (UserDto)", response = Long.class)
    long count();

    @ApiOperation(value = "Получение коллекции пользователей (UserDto) для коллекции e-mail. " +
            "В случае если коллекция e-mail пустая, то отдаётся Runtime исключение BadRequestException(HttpStatus.BAD_REQUEST)." +
            "Если запрос корректный, но не получилось получить коллекцию, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для запрашиваемых e-mail ов.", response = List.class),
            @ApiResponse(code = 400, message = "Список e-mail пуст"),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    List<UserDto> usersForEmails(@RequestBody List<String> emails);

    @ApiOperation(value = "Получение коллекции всех пользователей (UserDto) из бд. " +
            "Если запрос нет пользователей или не получилось получить коллекцию, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция всех пользователей.", response = List.class),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    List<UserDto> allUsers();

    @ApiOperation(value = "Проверка наличие пользователя (UserDto) с запрашиваемым e-mail в БД. " +
            "В случае если запрашиваемый e-mail пустой или не корректный, то отдаётся Runtime исключение BadRequestException(HttpStatus.BAD_REQUEST)."
            , response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь c запрашиваемым e-mail найден или не найден в БД.", response = Boolean.class),
            @ApiResponse(code = 409, message = "Некорректный e-mail  в запросе")})
    boolean checkIfExists(@PathVariable String email);

    @ApiOperation(value = "Установление пользователю (UserDto) статуса по его e-mail. " +
            "В случае e-mail пустой, не проходит проверку или статус пустой, то отдаётся Runtime исключение BadRequestException(HttpStatus.BAD_REQUEST)." +
            "В случае если пользователь UserDto по запрашиваемому e-mail не найден, то" +
            "Если запрос корректный, но не получилось обновить статус, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND). " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для запрашиваемых e-mail ов.", response = List.class),
            @ApiResponse(code = 400, message = "Список e-mail пуст"),
            @ApiResponse(code = 404, message = "Объект UserDto c запрашиваемым e-mail в БД не найден."),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    String updateStatus(@PathVariable String email,
                        @RequestBody UserStatus status);

}
