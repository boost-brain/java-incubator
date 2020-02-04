package boost.brain.course.tasks.controller;

import boost.brain.course.common.tasks.TaskDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Api
public interface CommonApiMethodSwaggerAnnotationsAble<T> {
    @ApiOperation(value = "Добавление новогоЗадания в формате JSON, если поля (кроме id, createDate и updateDate) " +
            "в передаваемом объекте пустые или не проходят валидацию, то отдаётся Runtime исключение " +
            "BadRequestException (HttpStatus.BAD_REQUEST). В случае если полученный объект был корректный, но не получилось его добавить, отдаётся " +
            "Runtime исключение ConflictException (HttpStatus.CONFLICT). Также при добавленииЗадания, производится " +
            "обращение к микросервису управления пользователями (users-service) для изменения статуса исполнителя." +
            "\"Занят\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создан TaskDto объект."),
            @ApiResponse(code = 409, message = "Поля (кроме id, createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 400, message = "Полученный объект был корректный, но не получилось его добавить.")
    })
    T create(@RequestBody T Dto);


    @ApiOperation(value = "Чтение задания в формате JSON по его id, если id < 1, то отдаётся Runtime исключение BadRequestException " +
            "(HttpStatus.BAD_REQUEST). Если задание не найдено, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Передан  TaskDto объект из БД.", response = TaskDto.class),
            @ApiResponse(code = 400, message = "Поле id неверное (<1)."),
            @ApiResponse(code = 404, message = "Объект  TaskDto в БД не найден.")
    })
    T read(@PathVariable long id);

    @ApiOperation(value = "Обновление данных задания ( TaskDto в формате JSON)," +
            " если поля (кроме createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию, " +
            "то отдаётся Runtime исключение BadRequestException (HttpStatus.BAD_REQUEST)." +
            "В случае если полученный объект был корректный, но не получилось его обновить, отдаётся " +
            "Runtime исключение ConflictException (HttpStatus.CONFLICT), а если по id задания " +
            "не найдено ранее сохранённое задание, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)" +
            "Также при обновлении задания, если поменялся исполнитель, то производится обращение к микросервису управления " +
            "пользователями (users-service) для изменения статуса исполнителя." +
            "\"Занят\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновлен  TaskDto объект.", response = TaskDto.class),
            @ApiResponse(code = 400, message = "Поля (кроме id, createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 404, message = "Объект  TaskDto в БД не найден."),
            @ApiResponse(code = 409, message = "Полученный объект был корректный, но не получилось его обновить.")
    })
    String update(@RequestBody T Dto);

    @ApiOperation(value = "Обновление данных задания ( TaskDto в формате JSON)," +
            " если поля (кроме createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию, " +
            "то отдаётся Runtime исключение BadRequestException (HttpStatus.BAD_REQUEST)." +
            "В случае если полученный объект был корректный, но не получилось его обновить, отдаётся " +
            "Runtime исключение ConflictException (HttpStatus.CONFLICT), а если по id задания " +
            "не найдено ранее сохранённое задание, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)" +
            "Также при обновлении задания, если поменялся исполнитель, то производится обращение к микросервису управления " +
            "пользователями (users-service) для изменения статуса исполнителя." +
            "\"Занят\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновлен  TaskDto объект.", response = TaskDto.class),
            @ApiResponse(code = 400, message = "Поля (кроме id, createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 404, message = "Объект  TaskDto в БД не найден."),
            @ApiResponse(code = 409, message = "Полученный объект был корректный, но не получилось его обновить.")
    })
    String patch(@RequestBody T Dto);

    @ApiOperation(value = "Удаление задания ( TaskDto) по id, если id < 1, то отдаётся Runtime исключение BadRequestException " +
            "(HttpStatus.BAD_REQUEST). Если задание не найдено, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Удален  TaskDto объект.", response = String.class),
            @ApiResponse(code = 400, message = "Запрашиваемый id пустой или не проходит валидацию."),
            @ApiResponse(code = 404, message = "Объект  TaskDto c запрашиваемым id в БД не найден."),
    })
    String delete(@PathVariable long id);

    @ApiOperation(value = "Получение количества всех хранимых заданий ( TaskDto)", response = Long.class)
    long count();

    @ApiOperation(value = "Получение коллекции заданий ( TaskDto) с пагинацией )(/{page}/{size}). В случае если page < 1 или " +
            "size < 1 отдаётся Runtime исключение BadRequestException (HttpStatus.BAD_REQUEST). " +
            "Если запрос корректный но не получилось получить коллекцию, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция с постраничным выводом.", response = List.class),
            @ApiResponse(code = 400, message = "Страница <1 или размер <1"),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    List<T> page(@PathVariable int page, @PathVariable int size);


    @ApiOperation(value = "Получение коллекции заданий ( TaskDto) для конкретного исполнителя по его e-mail. " +
            "В случае если email указан в некорректном формате, то отдаётся Runtime исключение " +
            "BadRequestException(HttpStatus.BAD_REQUEST). " +
            "Если запрос корректный, но не получилось получить коллекцию, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для запрашиваемого исполнителя.", response = List.class),
            @ApiResponse(code = 400, message = "Неверный формат email или email пуст"),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    List<T> tasksFor(@PathVariable String implementer);

    @ApiOperation(value = "Получение коллекции заданий ( TaskDto) для конкретного автора по его e-mail. " +
            "В случае если email указан в некорректном формате, то отдаётся Runtime исключение " +
            "BadRequestException(HttpStatus.BAD_REQUEST). " +
            "Если запрос корректный, но не получилось получить коллекцию, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для запрашиваемого автора.", response = List.class),
            @ApiResponse(code = 400, message = "Неверный формат email или email пуст"),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    List<T> tasksFrom(@PathVariable String author);

    @ApiOperation(value = "Получение коллекции заданий ( TaskDto) для конкретного проекта по его id. " +
            "В случае если id указан в некорректном формате, то отдаётся Runtime исключение " +
            "BadRequestException(HttpStatus.BAD_REQUEST). " +
            "Если запрос корректный, но не получилось получить коллекцию, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для запрашиваемого id проекта.", response = List.class),
            @ApiResponse(code = 400, message = "Неверный id проектат"),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    List<T> tasksIn(@PathVariable int project);


    @ApiOperation(value = "Получение коллекции заданий ( TaskDto) с фильтрацией по id проекта, автору (e-mail), исполнителю(e-mail). " +
            "В случае если параметры указаны в некорректном формате, то отдаётся Runtime исключение " +
            "BadRequestException(HttpStatus.BAD_REQUEST). " +
            "Если запрос корректный, но не получилось получить коллекцию, то отдаётся " +
            "Runtime исключение ConflictException(HttpStatus.CONFLICT).", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для заданного фильтра.", response = List.class),
            @ApiResponse(code = 409, message = "Нет данных для вывода коллекции.")
    })
    List<T> filter(@RequestParam(required = false, defaultValue = "0") int project,
                   @RequestParam(required = false, defaultValue = "") String author,
                   @RequestParam(required = false, defaultValue = "") String implementer);
}
