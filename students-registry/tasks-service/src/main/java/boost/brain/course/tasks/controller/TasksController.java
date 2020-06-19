package boost.brain.course.tasks.controller;

import boost.brain.course.common.tasks.TaskDto;
import boost.brain.course.common.users.UserStatus;
import boost.brain.course.tasks.Constants;
import boost.brain.course.tasks.controller.exceptions.BadRequestException;
import boost.brain.course.tasks.controller.exceptions.ConflictException;
import boost.brain.course.tasks.controller.exceptions.NotFoundException;
import boost.brain.course.tasks.repository.TasksRepository;
import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * RestController сервиса обработки Заданий.
 * в интерфейсе CommonApiMethodSwaggerAnnotationsAble содержится информация для сервиса документации Swagger
 */

@Log
@RestController
@RequestMapping(Constants.TASKS_CONTROLLER_PREFIX)
@Api(value = "REST контроллер для управления заданиями (TaskDto)")
public class TasksController implements  CommonApiMethodSwaggerAnnotationsAble <TaskDto>{

    private final TasksRepository tasksRepository;
    @Value("${users-service-url}")
    private String usersUrl;


    @Autowired
    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Override
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
            throw new BadRequestException();
        }
        long time = System.currentTimeMillis();
        taskDto.setCreateDate(time);
        taskDto.setUpdateDate(time);
        TaskDto result = tasksRepository.create(taskDto);
        if (result == null) {
            throw new ConflictException();
        } else {
            this.updateStatusForUser(result.getImplementer(), UserStatus.BUSY);
        }
        return result;
    }

    @Override
    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @Override
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
            throw new BadRequestException();
        }
        TaskDto readTaskDto = tasksRepository.read(taskDto.getId());
        if (readTaskDto == null) {
            throw new NotFoundException();
        }
        taskDto.setUpdateDate(System.currentTimeMillis());
        if (tasksRepository.update(taskDto)) {
            if (!readTaskDto.getImplementer().equals(taskDto.getImplementer())) {
                this.updateStatusForUser(taskDto.getImplementer(), UserStatus.BUSY);
            }
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new ConflictException();
        }
    }

    @Override
    @PutMapping(path = Constants.PUT_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String put(@RequestBody TaskDto taskDto) {
        if (taskDto.getId() < 1 ||
                taskDto.getProject() < 1 ||
                StringUtils.isEmpty(taskDto.getAuthor()) ||
                !this.checkEmail(taskDto.getAuthor()) ||
                StringUtils.isEmpty(taskDto.getImplementer()) ||
                !this.checkEmail(taskDto.getImplementer()) ||
                StringUtils.isEmpty(taskDto.getName()) ||
                StringUtils.isEmpty(taskDto.getText())) {
            throw new BadRequestException();
        }
        TaskDto readTaskDto = tasksRepository.read(taskDto.getId());
        if (readTaskDto == null) {
            throw new NotFoundException();
        }
        taskDto.setUpdateDate(System.currentTimeMillis());
        if (tasksRepository.update(taskDto)) {
            if (!readTaskDto.getImplementer().equals(taskDto.getImplementer())) {
                this.updateStatusForUser(taskDto.getImplementer(), UserStatus.BUSY);
            }
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new ConflictException();
        }
    }

    @Override
    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
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

    @Override
    @GetMapping(path = Constants.COUNT_PREFIX,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    long count() {
        return tasksRepository.count();
    }

    @GetMapping(path = Constants.PAGE_PREFIX + "/{page}/{size}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @Override
    @GetMapping(path = Constants.FOR_PREFIX + "/{implementer}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskDto> tasksFor(@PathVariable String implementer) {
        if (StringUtils.isEmpty(implementer) || !this.checkEmail(implementer)) {
            throw new BadRequestException();
        }
        List<TaskDto> result = tasksRepository.tasksFor(implementer);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @Override
    @GetMapping(path = Constants.FROM_PREFIX + "/{author}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskDto> tasksFrom(@PathVariable String author) {
        if (StringUtils.isEmpty(author) || !this.checkEmail(author)) {
            throw new BadRequestException();
        }
        List<TaskDto> result = tasksRepository.tasksFrom(author);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @Override
    @GetMapping(path = Constants.IN_PREFIX + "/{project}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @Override
    @GetMapping(path = Constants.FILTER_PREFIX,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskDto> filter(@RequestParam(required = false, defaultValue = "0") int project,
                                @RequestParam(required = false, defaultValue = "") String author,
                                @RequestParam(required = false, defaultValue = "") String implementer) {
        List<TaskDto> result = tasksRepository.filter(project, author, implementer);
        if (result == null) {
            throw new ConflictException();
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

    private void updateStatusForUser(String email, UserStatus status) {
        log.info("Start TasksController.updateStatusForUser");
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpEntity<UserStatus> request = new HttpEntity<>(status);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    usersUrl + Constants.UPDATE_STATUS_PREFIX + "/" + email,
                    request,
                    String.class);
            log.info("Response status code = " + response.getStatusCode());
        } catch (Exception e) {
            log.severe("TasksController.updateStatusForUser throw exception!");
            e.printStackTrace();
        }
        log.info("Finish TasksController.updateStatusForUser");
    }
}
