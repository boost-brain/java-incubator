package boost.brain.course.auth.controller;

import boost.brain.course.auth.Constants;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import boost.brain.course.common.auth.SessionCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api
public interface LoginControllerSwaggerAnnotations {

    @ApiOperation(value = "Вход в систему для зарегистрированного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Если вход в систему успешный, то в ответе будет передан заполненный объект Session." +
                    " В случае неуспешного входа поля объекта Session буду не заполнены. "),
    })
    @PostMapping(path = Constants.LOGIN_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Session login(@RequestBody Credentials credentials);


    @ApiOperation(value = "Выход из системы для пользователя, который залогинен в системе.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "В случае успешного выхода из системы в ответе будет - true. " +
                    "Если выход неуспешный, то false."),
    })
    @GetMapping(path = Constants.LOGOUT_PREFIX + "/{sessionId}")
    boolean logout(@PathVariable String sessionId);

    @ApiOperation(value = "Получение email и roles пользователя по sessionId, который передаётся в заголовке запроса.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сессия валидная, выдаётся email и roles пользователя"),
            @ApiResponse(code = 404, message = "Сессия не найдена или неактивна.")
    })
    @GetMapping(path = Constants.CHECK_PREFIX)
    SessionCheck getCheckSession(@RequestHeader("sessionId") String sessionId);
}
