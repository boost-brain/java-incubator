package boost.brain.course.tasks.controller;

import boost.brain.course.tasks.Constants;
import boost.brain.course.tasks.controller.dto.CommentDto;
import boost.brain.course.tasks.controller.exceptions.NotFoundException;
import boost.brain.course.tasks.repository.CommentsRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Constants.COMMENTS_CONTROLLER_PREFIX)
public class CommentsController {

    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsController(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @PostMapping(path = Constants.CREATE_PREFIX,
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommentDto create(@RequestBody CommentDto commentDto) {
        if (commentDto.getTaskId() < 1 ||
                StringUtils.isEmpty(commentDto.getAuthor()) ||
                    !this.checkEmail(commentDto.getAuthor()) ||
                        StringUtils.isEmpty(commentDto.getText())) {
            throw new NotFoundException();
        }
        long time = System.currentTimeMillis();
        commentDto.setCreateDate(time);
        commentDto.setUpdateDate(time);
        CommentDto result = commentsRepository.create(commentDto);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommentDto read(@PathVariable long id) {
        if (id < 1) {
            throw new NotFoundException();
        }
        CommentDto result = commentsRepository.read(id);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @PatchMapping(path = Constants.UPDATE_PREFIX,
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody CommentDto commentDto) {
        if (commentDto.getId() < 1 ||
                commentDto.getTaskId() < 1 ||
                    StringUtils.isEmpty(commentDto.getAuthor()) ||
                        !this.checkEmail(commentDto.getAuthor()) ||
                            StringUtils.isEmpty(commentDto.getText())) {
            throw new NotFoundException();
        }
        commentDto.setUpdateDate(System.currentTimeMillis());
        if (commentsRepository.update(commentDto)) {
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
        if (commentsRepository.delete(id)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(path = Constants.COUNT_PREFIX,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public long count() {
        return commentsRepository.count();
    }

    @GetMapping(path = Constants.FOR_PREFIX + "/{taskId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CommentDto> commentsFrom(@PathVariable long taskId) {
        //Проверяем идентификатор пользователя(автора)
        if (taskId < 1) {
            throw new NotFoundException();
        }
        List<CommentDto> result = commentsRepository.commentsFor(taskId);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.FILTER_PREFIX,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CommentDto> filter( @RequestParam(required = false, defaultValue = "0") long taskId,
                                    @RequestParam(required = false, defaultValue = "") String author) {
        List<CommentDto> result = commentsRepository.filter(taskId, author);
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
