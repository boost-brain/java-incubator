package boost.brain.course.tasks.controller;

import boost.brain.course.tasks.Constants;
import boost.brain.course.common.tasks.TaskDto;
import boost.brain.course.tasks.controller.exceptions.NotFoundException;
import boost.brain.course.tasks.repository.TasksRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.TASKS_CONTROLLER_PREFIX)
public class TasksController {

    private final TasksRepository tasksRepository;

    @Autowired
    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @PostMapping(path = Constants.CREATE_PREFIX,
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TaskDto create(@RequestBody TaskDto taskDto) {
        if (taskDto.getProject() < 1 ||
                StringUtils.isEmpty(taskDto.getAuthor()) ||
                    !this.checkEmail(taskDto.getAuthor()) ||
                        StringUtils.isEmpty(taskDto.getImplementer()) ||
                            !this.checkEmail(taskDto.getImplementer()) ||
                                StringUtils.isEmpty(taskDto.getName()) ||
                                    StringUtils.isEmpty(taskDto.getText())) {
            throw new NotFoundException();
        }
        long time = System.currentTimeMillis();
        taskDto.setCreateDate(time);
        taskDto.setUpdateDate(time);
        TaskDto result = tasksRepository.create(taskDto);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TaskDto read(@PathVariable long id) {
        if (id < 1) {
            throw new NotFoundException();
        }
        TaskDto result = tasksRepository.read(id);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @PatchMapping(path = Constants.UPDATE_PREFIX,
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody TaskDto taskDto) {
        if (taskDto.getId() < 1 ||
                taskDto.getProject() < 1 ||
                    StringUtils.isEmpty(taskDto.getAuthor()) ||
                        !this.checkEmail(taskDto.getAuthor()) ||
                            StringUtils.isEmpty(taskDto.getImplementer()) ||
                                !this.checkEmail(taskDto.getImplementer()) ||
                                    StringUtils.isEmpty(taskDto.getName()) ||
                                        StringUtils.isEmpty(taskDto.getText())) {
            throw new NotFoundException();
        }
        taskDto.setUpdateDate(System.currentTimeMillis());
        if (tasksRepository.update(taskDto)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @PutMapping(path = Constants.PUT_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String updatePut(@RequestBody TaskDto taskDto) {
        if (taskDto.getId() < 1 ||
                taskDto.getProject() < 1 ||
                StringUtils.isEmpty(taskDto.getAuthor()) ||
                !this.checkEmail(taskDto.getAuthor()) ||
                StringUtils.isEmpty(taskDto.getImplementer()) ||
                !this.checkEmail(taskDto.getImplementer()) ||
                StringUtils.isEmpty(taskDto.getName()) ||
                StringUtils.isEmpty(taskDto.getText())) {
            throw new NotFoundException();
        }
        taskDto.setUpdateDate(System.currentTimeMillis());
        if (tasksRepository.update(taskDto)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable long id) {
        if (id < 1) {
            throw new NotFoundException();
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
    public @ResponseBody long count() {
        return tasksRepository.count();
    }

    @GetMapping(path = Constants.PAGE_PREFIX + "/{page}/{size}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskDto> page(@PathVariable int page, @PathVariable int size) {
        //Проверяем номер страницы и размер
        if (page < 1 || size < 1) {
            throw new NotFoundException();
        }
        List<TaskDto> result = tasksRepository.getPage(page,size);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.FOR_PREFIX + "/{implementer}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskDto> tasksFor(@PathVariable String implementer) {
        if (StringUtils.isEmpty(implementer) || !this.checkEmail(implementer)) {
            throw new NotFoundException();
        }
        List<TaskDto> result = tasksRepository.tasksFor(implementer);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.FROM_PREFIX + "/{author}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskDto> tasksFrom(@PathVariable String author) {
        if (StringUtils.isEmpty(author) || !this.checkEmail(author)) {
            throw new NotFoundException();
        }
        List<TaskDto> result = tasksRepository.tasksFrom(author);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.IN_PREFIX + "/{project}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskDto> tasksIn(@PathVariable int project) {
        if (project < 1) {
            throw new NotFoundException();
        }
        List<TaskDto> result = tasksRepository.tasksIn(project);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.FILTER_PREFIX,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskDto> filter( @RequestParam(required = false, defaultValue = "0") int project,
                                 @RequestParam(required = false, defaultValue = "") String author,
                                 @RequestParam(required = false, defaultValue = "") String implementer) {
        List<TaskDto> result = tasksRepository.filter(project, author, implementer);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    private boolean checkEmail(final String email) {
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.isValid(email, null)) {
            return false;
        }
        return true;
    }
}
