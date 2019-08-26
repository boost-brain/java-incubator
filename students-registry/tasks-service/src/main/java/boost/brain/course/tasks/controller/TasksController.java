package boost.brain.course.tasks.controller;

import boost.brain.course.tasks.Constants;
import boost.brain.course.tasks.controller.dto.TaskDto;
import boost.brain.course.tasks.controller.exceptions.NotFoundException;
import boost.brain.course.tasks.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        //Проверяем идентификаторы проекта, автора и исполнителя задания
        if (taskDto.getProject() < 1 || taskDto.getAuthor() < 1 || taskDto.getImplementer() < 1) {
            throw new NotFoundException();
        }
        //Проверяем название задания
        if (taskDto.getName() == null || taskDto.getName().length() == 0) {
            throw new NotFoundException();
        }
        //Проверяем тект задания
        if (taskDto.getText() == null || taskDto.getText().length() == 0) {
            throw new NotFoundException();
        }
        //Инициализируем поля даты создания и обновления задания
        long time = System.currentTimeMillis();
        taskDto.setCreateDate(time);
        taskDto.setUpdateDate(time);

        TaskDto result = tasksRepository.create(taskDto);
        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TaskDto read(@PathVariable long id) {
        //Проверяем идентификатор задания
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
        //Проверяем идентификаторы задания, проекта, автора и исполнителя задания
        if (taskDto.getId() < 1 || taskDto.getProject() < 1 ||
                taskDto.getAuthor() < 1 || taskDto.getImplementer() < 1) {
            throw new NotFoundException();
        }
        //Проверяем название задания
        if (taskDto.getName() == null || taskDto.getName().length() == 0) {
            throw new NotFoundException();
        }
        //Проверяем тект задания
        if (taskDto.getText() == null || taskDto.getText().length() == 0) {
            throw new NotFoundException();
        }
        //Меняем дату последнего обновления
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
        //Проверяем идентификатор задания
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
    public List<TaskDto> tasksFor(@PathVariable int implementer) {
        //Проверяем идентификатор пользователя(исполнителя)
        if (implementer < 1) {
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
    public List<TaskDto> tasksFrom(@PathVariable int author) {
        //Проверяем идентификатор пользователя(автора)
        if (author < 1) {
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
        //Проверяем идентификатор проекта
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
                                 @RequestParam(required = false, defaultValue = "0") int author,
                                 @RequestParam(required = false, defaultValue = "0") int implementer) {
        List<TaskDto> result = tasksRepository.filter(project, author, implementer);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }
}
