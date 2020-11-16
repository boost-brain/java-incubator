package boost.brain.course.projects.controller;

import boost.brain.course.common.projects.ProjectDto;
import boost.brain.course.common.projects.ProjectStatus;
import boost.brain.course.projects.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Api
public interface ProjectControllerSwaggerAnnotations {
    @ApiOperation(value = "Добавление нового проекта. Обязательно должны быть заполнены поля: название, " +
            "описание, автор и Url проекта, в противном случае вернется ошибка 400.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создан новый проект."),
            @ApiResponse(code = 400, message = "Название, описание  и URL должны быть заполнены. Или ошибка сохранения в базу")
    })
    @ResponseBody
    @PostMapping(Constants.CREATE_PREFIX)
    ProjectDto create(@RequestBody ProjectDto projectDto);

    @ApiOperation(value = "Чтение проекта в формате JSON по его Id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создан новый проект."),
            @ApiResponse(code = 400, message = "Неверный Id."),
            @ApiResponse(code = 404, message = "Проект с запрашиваемым Id не найден.")

    })
    @ResponseBody
    @GetMapping(Constants.READ_PREFIX + "/{id}")
    ProjectDto read(@PathVariable int id);

    @ApiOperation(value = "Чтение количества проектов в БД.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Количество проектов."),

    })
    @ResponseBody
    @GetMapping(Constants.COUNT_PREFIX)
    int count();

    @ApiOperation(value = "Удаление проекта по его Id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Удален проект."),
            @ApiResponse(code = 400, message = "Неверный Id."),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден.")

    })
    @ResponseBody
    @DeleteMapping(Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    String delete(@PathVariable int id);

    @ApiOperation(value = "Чтение списка проектов с пагинацией.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список проектов."),
            @ApiResponse(code = 400, message = "Неверное значение номера страницы или неверный размер страницы."),
            @ApiResponse(code = 404, message = "Ошибка поучения результата.")

    })
    @ResponseBody
    @GetMapping(Constants.PAGE_PREFIX + "/{page}/{size}")
    Page<ProjectDto> page(
            @PathVariable int page,
            @PathVariable int size);

    @ApiOperation(value = "Обновление данных существующего проекта.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновлены данные проекта."),
            @ApiResponse(code = 400, message = "Название, описание, автор(должен быть неизменным)  и URL должны быть заполнены. Или ошибка сохранения в базу"),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден.")
    })
    @ResponseBody
    @PatchMapping(Constants.UPDATE_PREFIX)
    @ResponseStatus(HttpStatus.OK)
    String update(@RequestBody ProjectDto projectDto);

    @ApiOperation(value = "Получение коллекции проектов, по перечисленным значениям Id .")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция проектов."),
            @ApiResponse(code = 400, message = "Список iD пуст."),
            @ApiResponse(code = 404, message = "Не удалось получить список проектов, по заданному списку Id.")
    })
    @ResponseBody
    @PostMapping(Constants.FOR_IDS_PREFIX)
    List<ProjectDto> forIds(@RequestBody List<Integer> ids);

    @ApiOperation(value = "Получение коллекции всех проектов.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция проектов."),
            @ApiResponse(code = 404, message = "Не удалось получить список проектов.")
    })
    @ResponseBody
    @GetMapping(Constants.ALL_PREFIX)
    List<ProjectDto> all();

    @ApiOperation(value = "Проверка существования проекта с Id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ответ о наличии- true/false."),
            @ApiResponse(code = 400, message = "Неверный Id.")
    })
    @ResponseBody
    @GetMapping(Constants.IF_EXISTS_PREFIX + "/{id}")
    boolean ifExists(@PathVariable int id);

    @ApiOperation(value = "Обновление статуса проекта.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Статус проекта обновлён."),
            @ApiResponse(code = 400, message = "Ошибка во входных данных или при сохранении в базу."),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден.")
    })
    @ResponseBody
    @PatchMapping(Constants.UPDATE_PREFIX + Constants.STATUS_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    String updateStatus(@RequestBody ProjectStatus status, @PathVariable int id);

    @ApiOperation(value = "Добавление пользователя в список участвующих в проекте, " +
            "если пользователь был в списке ожидающих, то он из него удаляется.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь добавлен в список участвующих в проекте."),
            @ApiResponse(code = 400, message = "Ошибка во входных данных."),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден.")
    })
    @ResponseBody
    @GetMapping("/{id}" + Constants.PARTICIPATING_USERS + Constants.CREATE_PREFIX + "/{email}")
    @ResponseStatus(HttpStatus.OK)
    String createParticipatingUser(@PathVariable String email, @PathVariable int id);

    @ApiOperation(value = "Получение коллекции всех пользователей участвующих в проекте.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция всех пользователей участвующих в проекте."),
            @ApiResponse(code = 400, message = "Ошибка во входных данных."),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден.")
    })
    @ResponseBody
    @GetMapping("/{id}" + Constants.PARTICIPATING_USERS + Constants.ALL_PREFIX)
    Set<String> allParticipatingUsers(@PathVariable int id);

    @ApiOperation(value = "Удаление пользователя из списка участвующих в проекте.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удалён из списка участвующих в проекте."),
            @ApiResponse(code = 400, message = "Ошибка во входных данных."),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден или ненайден пользователь.")
    })
    @ResponseBody
    @DeleteMapping("/{id}" + Constants.PARTICIPATING_USERS + Constants.DELETE_PREFIX + "/{email}")
    @ResponseStatus(HttpStatus.OK)
    String deleteParticipatingUser(@PathVariable String email, @PathVariable int id);

    @ApiOperation(value = "Добавление пользователя в список ожидающих участия в проекте.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь добавлен в список ожидающих участия в проекте."),
            @ApiResponse(code = 400, message = "Ошибка во входных данных, " +
                    "или если пользователь уже есть в списке участвующих в проекте."),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден.")
    })
    @ResponseBody
    @GetMapping("/{id}" + Constants.WAITING_USERS + Constants.CREATE_PREFIX + "/{email}")
    @ResponseStatus(HttpStatus.OK)
    String createWaitingUser(@PathVariable String email, @PathVariable int id);

    @ApiOperation(value = "Получение коллекции всех пользователей ожидающих участия в проекте.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Коллекция всех пользователей ожидающих участия в проекте."),
            @ApiResponse(code = 400, message = "Ошибка во входных данных."),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден.")
    })
    @ResponseBody
    @GetMapping("/{id}" + Constants.WAITING_USERS + Constants.ALL_PREFIX)
    Set<String> allWaitingUsers(@PathVariable int id);

    @ApiOperation(value = "Удаление пользователя из списка ожидающих участия в проекте.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удалён из списка ожидающих участия в проекте."),
            @ApiResponse(code = 400, message = "Ошибка во входных данных."),
            @ApiResponse(code = 404, message = "Проект с указанным Id не найден или ненайден пользователь.")
    })
    @ResponseBody
    @DeleteMapping("/{id}" + Constants.WAITING_USERS + Constants.DELETE_PREFIX + "/{email}")
    @ResponseStatus(HttpStatus.OK)
    String deleteWaitingUser(@PathVariable String email, @PathVariable int id);
}
