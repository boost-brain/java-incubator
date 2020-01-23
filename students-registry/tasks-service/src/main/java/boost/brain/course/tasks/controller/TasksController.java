package boost.brain.course.tasks.controller;

import boost.brain.course.common.tasks.TaskDto;
import boost.brain.course.common.users.UserStatus;
import boost.brain.course.tasks.Constants;
import boost.brain.course.tasks.controller.exceptions.BadRequestException;
import boost.brain.course.tasks.controller.exceptions.ConflictException;
import boost.brain.course.tasks.controller.exceptions.NotFoundException;
import boost.brain.course.tasks.repository.TasksRepository;
import boost.brain.course.tasks.service.OtherServiceCommunication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping(Constants.TASKS_CONTROLLER_PREFIX)
@Api(value = "Rest контроллер для для Задач (Task)")
public class TasksController {

    private final TasksRepository tasksRepository;
    private final OtherServiceCommunication otherServiceCommunication;

    @Autowired
    public TasksController(TasksRepository tasksRepository, OtherServiceCommunication otherServiceCommunication) {
        this.tasksRepository = tasksRepository;
        this.otherServiceCommunication = otherServiceCommunication;
    }


    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Добавление нового задания в базу", response = TaskDto.class)
    public TaskDto create(@RequestBody TaskDto taskDto) {
        if (isTaskDtoFieldsIsEmpty(taskDto)) {
            throw new BadRequestException();
        }
        setCurrentTime(taskDto);
        return createTaskDtoByRepository(taskDto);
    }


    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Чтение одного Задания по его id", response = TaskDto.class)
    public TaskDto read(@PathVariable long id) {
        if (id < 1) {
            throw new BadRequestException();
        }
        TaskDto result = tasksRepository.read(id);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    /**
     * ToDO: В текущей версии Update и UpdatePut выполняют одно и тоже. Хотя второй метод должен делать Patch
     * @throws Exception
     */
    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Обновление данных Задания", response = String.class)
    public String update(@RequestBody TaskDto taskDto) {
        return updateTaskDto(taskDto);
    }


    @PutMapping(path = Constants.PUT_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Обновление данных Задания, а если данных нет, то создание нового Задания", response = String.class)
    public String updatePut(@RequestBody TaskDto taskDto) {
        return updateTaskDto(taskDto);
    }


    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Удаление одного Задания по его id", response = TaskDto.class)
    public String delete(@PathVariable long id) {
        if (id < 1) {
            throw new BadRequestException();
        }
        if (tasksRepository.delete(id)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(path = Constants.COUNT_PREFIX,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Считает количество Заданий в БД", response = Long.class)
    public @ResponseBody
    long count() {
        return tasksRepository.count();
    }

    @GetMapping(path = Constants.PAGE_PREFIX + "/{page}/{size}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Считывание коллекции Заданий c пагинацией", response = List.class)
    public List<TaskDto> page(@PathVariable int page, @PathVariable int size) {
        //Проверяем номер страницы и размер
        if (page < 1 || size < 1) {
            throw new BadRequestException();
        }
        List<TaskDto> result = tasksRepository.getPage(page, size);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @GetMapping(path = Constants.FOR_PREFIX + "/{implementer}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Считывание коллекции Заданий для конкретного студента", response = List.class)
    public List<TaskDto> tasksFor(@PathVariable String implementer) {
        checkEmailAndNotEmpty(implementer);
        List<TaskDto> result = tasksRepository.tasksFor(implementer);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }


    @GetMapping(path = Constants.FROM_PREFIX + "/{author}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Считывание коллекции Заданий для конкретного автора", response = List.class)
    public List<TaskDto> tasksFrom(@PathVariable String author) {
        checkEmailAndNotEmpty(author);
        List<TaskDto> result = tasksRepository.tasksFrom(author);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @GetMapping(path = Constants.IN_PREFIX + "/{project}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Считывание коллекции Заданий для проекта", response = List.class)
    public List<TaskDto> tasksIn(@PathVariable int project) {
        if (project < 1) {
            throw new BadRequestException();
        }
        List<TaskDto> result = tasksRepository.tasksIn(project);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @GetMapping(path = Constants.FILTER_PREFIX,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Считывание коллекции Заданий с фильтрацией ", response = List.class)
    public List<TaskDto> filter(@RequestParam(required = false, defaultValue = "0") int project,
                                @RequestParam(required = false, defaultValue = "") String author,
                                @RequestParam(required = false, defaultValue = "") String implementer) {
        List<TaskDto> result = tasksRepository.filter(project, author, implementer);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    /**
     * Проверка электронной почты на валидность
     *
     * @param email String email
     * @return true если валидная
     */
    private boolean checkEmail(final String email) {
        return new EmailValidator().isValid(email, null);
    }


    /**
     * Проверяет , что поля не пустые
     *
     * @param taskDto Проверяемое
     * @return да, если есть пустые поля
     */
    private boolean isTaskDtoFieldsIsEmpty(@RequestBody TaskDto taskDto) {
        return taskDto.getId() < 1 ||
                taskDto.getProject() < 1 ||
                StringUtils.isEmpty(taskDto.getAuthor()) ||
                !this.checkEmail(taskDto.getAuthor()) ||
                StringUtils.isEmpty(taskDto.getImplementer()) ||
                !this.checkEmail(taskDto.getImplementer()) ||
                StringUtils.isEmpty(taskDto.getName()) ||
                StringUtils.isEmpty(taskDto.getText());
    }

    /**
     * Устанавливает текущее время в taskDto
     *
     * @param taskDto , объект TaskDto
     */
    private void setCurrentTime(@RequestBody TaskDto taskDto) {
        long time = System.currentTimeMillis();
        taskDto.setCreateDate(time);
        taskDto.setUpdateDate(time);
    }

    /**
     * Создание сущности в БД
     *
     * @param taskDto объект TaskDto
     * @return сохраненный объект TaskDto
     * throw ConflictException();
     */
    private TaskDto createTaskDtoByRepository(@RequestBody TaskDto taskDto) {
        TaskDto result = tasksRepository.create(taskDto);
        if (result == null) {
            throw new ConflictException();
        } else {
            otherServiceCommunication.updateStatusForUser(result.getImplementer(), UserStatus.BUSY);
        }
        return result;
    }

    /**
     * проверяет электронную почту и имя на "непустоту"
     *
     * @param name String
     */
    private void checkEmailAndNotEmpty(@PathVariable String name) {
        if (StringUtils.isEmpty(name) || !this.checkEmail(name)) {
            throw new BadRequestException();
        }
    }

    /**
     * Обновление данных taskDTO в базе
     *
     * @param taskDto taskDto
     * @return фраза
     */

    private String updateTaskDto(@RequestBody TaskDto taskDto) {
        if (isTaskDtoFieldsIsEmpty(taskDto)) {
            throw new BadRequestException();
        }
        TaskDto readTaskDto = tasksRepository.read(taskDto.getId());
        if (readTaskDto == null) {
            throw new NotFoundException();
        }
        taskDto.setUpdateDate(System.currentTimeMillis());
        if (tasksRepository.update(taskDto)) {
            if (!readTaskDto.getImplementer().equals(taskDto.getImplementer())) {
                otherServiceCommunication.updateStatusForUser(taskDto.getImplementer(), UserStatus.BUSY);
            }
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new ConflictException();
        }
    }

}
