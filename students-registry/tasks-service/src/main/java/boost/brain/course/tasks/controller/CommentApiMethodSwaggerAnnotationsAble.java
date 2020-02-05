package boost.brain.course.tasks.controller;

import boost.brain.course.common.tasks.CommentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Интерфейс аанотирования методов контроллера CommentsController для Swagger
 */

@Api
public interface CommentApiMethodSwaggerAnnotationsAble {
    @ApiOperation(value = "Добавление нового нового комментария к заданию в формате JSON, если поля (кроме id, createDate и updateDate) " +
            "в передаваемом объекте пустые или не проходят валидацию, то отдаётся Runtime исключение NotFoundException, такое же " +
            "искючение делается, если задание не найдено в базе.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Передан  CommentDto объект из БД.", response = CommentDto.class),
            @ApiResponse(code = 404, message = "Поля (кроме id, createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию. Или не найдено задание."),
    })
    CommentDto create(@RequestBody CommentDto commentDto);

    @ApiOperation(value = "Чтение комментария к заданию в формате JSON по его id, если id < 1, если задание не найдено, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создан CommentDto объект."),
            @ApiResponse(code = 404, message = "Объект  TaskDto в БД не найден."),
    })
    CommentDto read(@PathVariable long id);

    @ApiOperation(value = "Обновление данных комментария (CommentDto в формате JSON)," +
            " если поля (кроме createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию, " +
            "то отдаётся Runtime исключение BadRequestException (HttpStatus.BAD_REQUEST)." +
            "В случае если полученный объект был корректный, но не получилось его обновить, отдаётся " +
            "Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновлен CommentDto объект."),
            @ApiResponse(code = 404, message = "Объект CommentDto в БД не найден, или обновляемые поля некорректные."),
    })
    String update(@RequestBody CommentDto commentDto);

    @ApiOperation(value = "Удаление комментария (CommentDto) по id, если id < 1, и если задание не найдено, то отдаётся Runtime исключение NotFoundException (HttpStatus.NOT_FOUND)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Удален CommentDto объект.", response = String.class),
            @ApiResponse(code = 404, message = "Объект CommentDto c запрашиваемым id в БД не найден."),
    })
    String delete(@PathVariable long id);

    @ApiOperation(value = "Получение количества всех хранимых комментариев (CommentDto)", response = Long.class)
    long count();

    @ApiOperation(value = "Получение коллекции заданий (CommentDto) для конкретного задания по его id. " +
            "В случае если id указан в некорректном формате, или если запрос корректный, но не получилось получить коллекцию, то отдаётся " +
            "Runtime исключение NotFoundException.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для запрашиваемого задания.", response = List.class),
            @ApiResponse(code = 404, message = "Нет данных для вывода коллекции.")
    })
    List<CommentDto> commentsFrom(@PathVariable long taskId);

    @ApiOperation(value = "Получение коллекции комментариев (CommentDto) с фильтрацией по id задания, автору (e-mail) " +
            "В случае если параметры указаны в некорректном формате, то отдаётся Runtime исключение NotFoundException.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция для заданного фильтра.", response = List.class),
            @ApiResponse(code = 404, message = "Нет данных для вывода коллекции.")
    })
    List<CommentDto> filter(@RequestParam(required = false, defaultValue = "0") long taskId,
                            @RequestParam(required = false, defaultValue = "") String author);
}
