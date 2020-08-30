package boost.brain.course.messages.controller;

import boost.brain.course.common.messages.MessageDto;
import boost.brain.course.messages.Constants;
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
public interface MessagesControllerSwaggerAnnotation {

    @ApiOperation(value = "Добавление нового сообщения. ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создано новое сообщение."),
            @ApiResponse(code = 400, message = "Неверно указано имя автора, получателя или пустое сообщение."),
            @ApiResponse(code = 409, message = "Неудалось записать новое сообщение в БД."),
    })
    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    MessageDto create(@RequestBody MessageDto messageDto);

    @ApiOperation(value = "Чтение сообщения.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщение успешно прочитано."),
            @ApiResponse(code = 400, message = "Неверное Id в запросе сообщения."),
            @ApiResponse(code = 404, message = "Запрашиваемое сообщение не найдено."),
    })
    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    MessageDto read(@PathVariable long id);

    @ApiOperation(value = "Изменение сообщения.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщение успешно обновлено."),
            @ApiResponse(code = 400, message = "Неверное Id в запросе сообщения."),
            @ApiResponse(code = 404, message = "Запрашиваемое сообщение не найдено."),
            @ApiResponse(code = 409, message = "Неудалось обновить сообщение."),
    })
    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    String update(@RequestBody MessageDto messageDto);

    @ApiOperation(value = "Удаление сообщения.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщение успешно удалено."),
            @ApiResponse(code = 400, message = "Неверное Id в запросе на удаление сообщения."),
            @ApiResponse(code = 404, message = "Удаляемое сообщение не найдено."),
    })
    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    String delete(@PathVariable long id);

    @ApiOperation(value = "Количество всех сообщений.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Количество всех сообщений в БД."),
    })
    @GetMapping(path = Constants.COUNT_PREFIX)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    long count();

    @ApiOperation(value = "Получение всех сообщений пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщения успешно получены."),
            @ApiResponse(code = 400, message = "e-mail пользователя неверен либо пуст."),
            @ApiResponse(code = 409, message = "Сообщения для пользователя не найдены."),
    })
    @GetMapping(path = Constants.ALL_MESSAGES_FOR_USER + "/{email}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Map<String, List<MessageDto>> allMessagesForUser(@PathVariable String email);

    @ApiOperation(value = "Удаление всех сообщений пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщения успешно удалены."),
            @ApiResponse(code = 400, message = "e-mail пользователя неверен либо пуст."),
            @ApiResponse(code = 404, message = "Сообщения пользователя не найдены."),
    })
    @DeleteMapping(path = Constants.DELETE_ALL_MESSAGES_FOR_USER + "/{email}")
    @ResponseStatus(HttpStatus.OK)
    String deleteAllMessagesForUser(@PathVariable String email);

    @ApiOperation(value = "Проставление для всех сообщений, передаваемой коллекции, меток о прочтении.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метки о прочтении проставлены."),
            @ApiResponse(code = 400, message = "Пустая коллекция для изменения данных."),
            @ApiResponse(code = 404, message = "Сообщения не найдены."),
    })
    @PatchMapping(path = Constants.MESSAGES_AS_READ)
    @ResponseStatus(HttpStatus.OK)
    String messagesAsRead(@RequestBody List<Integer> ids);
}
