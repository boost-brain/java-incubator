package boost.brain.course.auth.controller;

import boost.brain.course.common.auth.Credentials;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api
public interface CredentialsControllerApiMethodSwaggerAnnotation {

    @ApiOperation(value = "Сохранение нового имени пользователя и его пароля в бд. Пароль шифруется с помощью алгоритма MD5 ( https://ru.wikipedia.org/wiki/MD5 )." +
            "В случае если пароль создать не удается, то АПИ метод возвращает false. Если объект создан успешно, то возвращается true.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создан, или не создан, Credentials объект. Смотри возвращаемое значение.")
    })
    boolean create(@RequestBody Credentials credentials);

    @ApiOperation(value = "Изменение данных пользователя и/или его пароля в  для входа в приложение.В случае если обновление данных не удается, то АПИ метод " +
            "возвращает false. Если процедура пройдена успешно, то возвращается true.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Изменен, или не изменён, Credentials объект. Смотри возвращаемое значение.")
    })
    boolean update(@RequestBody Credentials credentials);

    @ApiOperation(value = "Удаление имени пользователя и его пароля из бд, по имени пользователя. Если объект в бд не найден, то ответом сервера будет HttpStatus.NOT_FOUND.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Объект Credentials успешно удален."),
            @ApiResponse(code = 404, message = "Удаляемый объект не найден в бд.")
    })
    void delete(@PathVariable String login);
}
