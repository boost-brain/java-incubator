package boost.brain.course.auth.controller;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api
public interface LoginControllerApiMethodSwaggerAnnotation {

    @ApiOperation(value = "Осуществляет вход в приложение, по введенному логину и паролю (Credentials). Возвращает объект новой сессии (Session)." +
            "Вход невозможен из за неверных данных в Credentials, то возвращает null.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создание Session объект.")
    })
    public Session login(@RequestBody Credentials credentials);

    @ApiOperation(value = "Закрывает текущую сессию по значению ID Session. Если сессия найдена и успешно закрыта, то возвращает true, в иных случчаях false. ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Закрытие сессии.")
    })
    boolean logout(@PathVariable String sessionId);

    @ApiOperation(value = "Проверяет наличие сессии в активных по значению ID Session. Если сессия найдена и активна    , то возвращает true, в иных случчаях false. ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Проверка сессии на активность.")
    })
    boolean checkSession(@PathVariable String sessionId);
}
