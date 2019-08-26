package boost.brain.course.tasks.controller;

import boost.brain.course.tasks.Constants;
import boost.brain.course.tasks.controller.dto.CommentDto;
import boost.brain.course.tasks.controller.exceptions.NotFoundException;
import boost.brain.course.tasks.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.COMMENTS_CONTROLLER_PREFIX)
public class CommentsController {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentsController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping(path = Constants.CREATE_PREFIX,
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommentDto create(@RequestBody CommentDto commentDto) {
        if (commentDto.getTaskId() < 0 || commentDto.getAuthor() < 0) {
            throw new NotFoundException();
        }
        if (commentDto.getText() == null || commentDto.getText().length() == 0) {
            throw new NotFoundException();
        }
        long time = System.currentTimeMillis();
        commentDto.setCreateDate(time);
        commentDto.setUpdateDate(time);
        CommentDto result = commentRepository.create(commentDto);
        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommentDto read(@PathVariable long id) {
        //Проверяем идентификатор комментария
        if (id < 1) {
            throw new NotFoundException();
        }
        CommentDto result = commentRepository.read(id);
        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }

    @PatchMapping(path = Constants.UPDATE_PREFIX,
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody CommentDto commentDto) {
        //Проверяем идентификаторы комментария, задания и автора
        if (commentDto.getId() < 1 || commentDto.getTaskId() < 1 || commentDto.getAuthor() < 1) {
            throw new NotFoundException();
        }
        //Проверяем тект комментария
        if (commentDto.getText() == null || commentDto.getText().length() == 0) {
            throw new NotFoundException();
        }
        //Меняем дату последнего обновления
        commentDto.setUpdateDate(System.currentTimeMillis());
        if (commentRepository.update(commentDto)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable long id) {
        //Проверяем идентификатор комментария
        if (id < 1) {
            throw new NotFoundException();
        }
        if (commentRepository.delete(id)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(path = Constants.COUNT_PREFIX,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public long count() {
        return commentRepository.count();
    }

    @GetMapping(path = Constants.FOR_PREFIX + "/{taskId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CommentDto> commentsFrom(@PathVariable long taskId) {
        //Проверяем идентификатор пользователя(автора)
        if (taskId < 1) {
            throw new NotFoundException();
        }
        List<CommentDto> result = commentRepository.commentsFor(taskId);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.FILTER_PREFIX,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CommentDto> filter( @RequestParam(required = false, defaultValue = "0") long taskId,
                                    @RequestParam(required = false, defaultValue = "0") int author) {
        List<CommentDto> result = commentRepository.filter(taskId, author);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }
}
