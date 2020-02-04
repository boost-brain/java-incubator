package boost.brain.course.tasks.controller;

import boost.brain.course.common.tasks.TaskDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

@Api
public interface CommonApiMethodSwaggerAnnotationsAble<T>{
    @ApiOperation(value = "Добавление нового задания в формате JSON, если поля (кроме id, createDate и updateDate) " +
            "в передаваемом объекте пустые или не проходят валидацию, то отдаётся Runtime исключение " +
            "BadRequestException (HttpStatus.BAD_REQUEST). " +
            "В случае если полученный объект был корректный, но не получилось его добавить, отдаётся " +
            "Runtime исключение ConflictException (HttpStatus.CONFLICT). Также при добавлении задания, производится " +
            "обращение к микросервису управления пользователями (users-service) для изменения статуса исполнителя." +
            "\"Занят\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создан TaskDto объект.", response = TaskDto.class),
            @ApiResponse(code = 409, message = "Поля (кроме id, createDate и updateDate) в передаваемом объекте пустые или не проходят валидацию."),
            @ApiResponse(code = 400, message = "Полученный объект был корректный, но не получилось его добавить.")
    })
     T create(@RequestBody T taskDto);
}
