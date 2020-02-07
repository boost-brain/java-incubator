package boost.brain.course.messages.controller;

import boost.brain.course.common.messages.MessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Api
public interface MessagesControllerApiMethodSwaggerAnnotationsAble {
    @ApiOperation(value = "Добавление нового сообщения в формате JSON.Если поля отправитель (author), получатель (recipient), текст сообщения (text) " +
            "в передаваемом объекте пустые или не проходят валидацию, то отдаётся Runtime исключение " +
            "BadRequestException (HttpStatus.BAD_REQUEST). В случае если полученный объект был корректный, но не получилось его добавить, отдаётся " +
            "Runtime исключение ConflictException (HttpStatus.CONFLICT).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создан MessageDto объект."),
            @ApiResponse(code = 409, message = "Поля отправитель (author), получатель (recipient), текст сообщения (text) в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 400, message = "Полученный объект был корректный, но не получилось его добавить.")
    })
    MessageDto create(@RequestBody MessageDto messageDto);

    @ApiOperation(value = "Чтение сообщения в формате JSON по его id, если id < 1, то отдаётся Runtime исключение BadRequestException " +
            "(HttpStatus.BAD_REQUEST). Если сообщение не найдено, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Передан  MessageDto объект из БД.", response = MessageDto.class),
            @ApiResponse(code = 400, message = "Поле id неверное (<1)."),
            @ApiResponse(code = 404, message = "Объект  TaskDto в БД не найден.")
    })
    MessageDto read(@PathVariable long id);

    @ApiOperation(value = "Обновление данных сообщения (MessageDto в формате JSON). В случае, если id<1, или текст сообщения пустой," +
            "Runtime исключение BadRequestException (HttpStatus.BAD_REQUEST)." +
            "В случае если полученный объект был корректный, но не получилось его обновить, отдаётся " +
            "Runtime исключение ConflictException (HttpStatus.CONFLICT)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновлен MessageDto объект.", response = MessageDto.class),
            @ApiResponse(code = 400, message = "Поле id<1, или текст сообщения пустой в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 409, message = "Полученный объект был корректный, но не получилось его обновить.")
    })
    String update(@RequestBody MessageDto messageDto);

    @ApiOperation(value = "Удаление сообщения (MessageDto) по id, если id < 1 или сообщение не найдено то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Удален MessageDto объект.", response = String.class),
            @ApiResponse(code = 404, message = "Объект MessageDto c запрашиваемым id в БД не найден, или id<0."),
    })
    String delete(@PathVariable long id);

    @ApiOperation(value = "Получение количества всех хранимых сообщений (MessageDto)", response = Long.class)
    long count();

    @ApiOperation(value = "Получение словаря сообщений (MessageDto) для конкретного автора и получателя по e-mail. " +
            "В случае если email указан в некорректном формате, то отдаётся Runtime исключение " +
            "BadRequestException(HttpStatus.BAD_REQUEST). " +
            "Если запрос корректный, но не получилось получить данные, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для запрашиваемого автора и получателя.", response = Map.class),
            @ApiResponse(code = 400, message = "Неверный формат email или email пуст"),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    Map<String, List<MessageDto>> allMessagesForUser(@PathVariable String email);

    @ApiOperation(value = "Удаление всех сообщений (MessageDto) для конкретного автора и получателя по его e-mail. " +
            "В случае если email указан в некорректном формате, то отдаётся Runtime исключение " +
            "BadRequestException(HttpStatus.BAD_REQUEST). " +
            "Если запрос корректный, но не получилось получить данные, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все сообщения для запрашиваемого пользователя удалены.", response = String.class),
            @ApiResponse(code = 400, message = "Неверный формат email или email пуст."),
            @ApiResponse(code = 409, message = "Не удалось удалить сообщения.")
    })
    String deleteAllMessagesForUser(@PathVariable String email);

    @ApiOperation(value = "Пометить все сообщения (MessageDto) как прочитанные, для конкретного пользователя по его e-mail. " +
            "В случае если email указан в некорректном формате, то отдаётся Runtime исключение " +
            "BadRequestException(HttpStatus.BAD_REQUEST). " +
            "Если запрос корректный, но не получилось пометить сообщения как прочитанные, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все сообщения для запрашиваемого пользователя помечены как прочитанные.", response = String.class),
            @ApiResponse(code = 400, message = "Неверный формат email или email пуст."),
            @ApiResponse(code = 409, message = "Не удалось пометить сообщения.")
    })
    String messagesAsRead(@RequestBody List<Integer> ids);

}
