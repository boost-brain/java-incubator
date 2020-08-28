package boost.brain.course.projects.controller;

import boost.brain.course.common.projects.ProjectDto;
import boost.brain.course.projects.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
public interface ProjectControllerSwaggerAnnotations {
    @ApiOperation(value = "Добавление нового проекта. Обязательно должны быть заполнены поля: название, " +
            "описание и Url проекта, в противном случае вернется ошибка 400.")
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
            @ApiResponse(code = 400, message = "Название, описание  и URL должны быть заполнены. Или ошибка сохранения в базу"),
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
}
