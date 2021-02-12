package boost.brain.course.auth.controller;

import boost.brain.course.auth.Constants;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.UserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Api
public interface CredentialsControllerSwaggerAnnotations {

    @ApiOperation(value = "Добавление нового пользователя. Обязательно должны быть заполнены поля и роль администратора." +
            " Возращает True если пользователь создан, или False, в случае если пользователя создать не удалось.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создание нового пользователя. Возращает True если пользователь создан, " +
                    "или False, в случае если пользователя создать не удалось."),
            @ApiResponse(code = 403, message = "Ошибка прав доступа."),
    })
    @PostMapping(path = Constants.CREATE_PREFIX, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    boolean create(
            @RequestBody Credentials credentials,
            @RequestAttribute("user-roles") Set<UserRole> rolesRequest
    );

    @ApiOperation(value = "Обновление данных авторизации пользователя. Обязательно должны быть заполнены поля и " +
            "роль администратора.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновление данных авторизации пользователя. Возращает True если данные " +
                    "пользователя успешно изменены, или False, в случае если обновить данные не удалось."),
            @ApiResponse(code = 403, message = "Ошибка прав доступа.")
    })
    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    boolean update(@RequestBody Credentials credentials, @RequestAttribute("user-roles") Set<UserRole> rolesRequestAttr);

    @ApiOperation(value = "Удаление пользователя. Обязательно должны быть права администратора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Удаление пользователя. Возращает True если данные пользователя успешно " +
                    "изменены, или False, в случае если обновить данные не удалось."),
            @ApiResponse(code = 403, message = "Ошибка прав доступа.")

    })
    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{login}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable String login, @RequestAttribute("user-roles") Set<UserRole> rolesRequest);

    @ApiOperation(value = "Получение набора ролей пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция ролей для пользователя."),
            @ApiResponse(code = 404, message = "Пользователь не найден.")

    })
    @GetMapping(path = Constants.USER_ROLE_PREFIX + Constants.READ_PREFIX + "/{login}")
    Set<UserRole> readUserRoles(@PathVariable String login);

    @ApiOperation(value = "Обновление набора ролей пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновление набора ролей пользователя. Обязательно должны быть заполнены " +
                    "поля (только нельзя давать права администратора) и роль администратора. Возращает True если " +
                    "данные о ролях пользователя успешно изменены, или False, в случае если обновить данные не удалось."),
            @ApiResponse(code = 403, message = "Ошибка прав доступа.")
    })
    @PatchMapping(
            path = Constants.USER_ROLE_PREFIX + Constants.UPDATE_PREFIX + "/{login}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    boolean updateUserRoles(
            @RequestBody Set<UserRole> roles,
            @PathVariable String login,
            @RequestAttribute("user-roles") Set<UserRole> rolesRequest
    );
}
