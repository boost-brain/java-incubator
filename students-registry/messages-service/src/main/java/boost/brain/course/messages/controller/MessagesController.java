package boost.brain.course.messages.controller;

import boost.brain.course.common.messages.MessageDto;
import boost.brain.course.messages.Constants;
import boost.brain.course.messages.controller.exceptions.BadRequestException;
import boost.brain.course.messages.controller.exceptions.ConflictException;
import boost.brain.course.messages.controller.exceptions.NotFoundException;
import boost.brain.course.messages.repository.MessagesRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(Constants.MESSAGES_CONTROLLER_PREFIX)
public class MessagesController {

    private final MessagesRepository messagesRepository;

    public MessagesController(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto create(@RequestBody MessageDto messageDto) {
        if (StringUtils.isEmpty(messageDto.getAuthor()) ||
                !this.checkEmail(messageDto.getAuthor()) ||
                StringUtils.isEmpty(messageDto.getRecipient()) ||
                !this.checkEmail(messageDto.getRecipient())  ||
                StringUtils.isEmpty(messageDto.getText())) {
            throw new BadRequestException();
        }
        long time = System.currentTimeMillis();
        messageDto.setCreateDate(time);
        messageDto.setEditDate(0);
        messageDto.setReadDate(0);
        messageDto.setRead(false);
        messageDto.setEdited(false);

        MessageDto result = messagesRepository.create(messageDto);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto read(@PathVariable long id) {
        if (id < 1) {
            throw new BadRequestException();
        }
        MessageDto result = messagesRepository.read(id);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }


    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody MessageDto messageDto) {
        if (messageDto.getId() < 1 || StringUtils.isEmpty(messageDto.getText())) {
            throw new BadRequestException();
        }
        messageDto.setEditDate(System.currentTimeMillis());
        messageDto.setEdited(true);
        if (messagesRepository.update(messageDto)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new ConflictException();
        }
    }

    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable long id) {
        if (id < 1) {
            throw new NotFoundException();
        }
        if (messagesRepository.delete(id)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(path = Constants.COUNT_PREFIX)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody long count() {
        return messagesRepository.count();
    }

    @GetMapping(path = Constants.ALL_MESSAGES_FOR_USER + "/{email}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, List<MessageDto>> allMessagesForUser(@PathVariable String email) {
        if (StringUtils.isEmpty(email) || !this.checkEmail(email)) {
            throw new BadRequestException();
        }
        Map<String, List<MessageDto>> result = messagesRepository.getAllMessageForUser(email);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @DeleteMapping(path = Constants.DELETE_ALL_MESSAGES_FOR_USER + "/{email}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAllMessagesForUser(@PathVariable String email) {
        if (StringUtils.isEmpty(email) || !this.checkEmail(email)) {
            throw new BadRequestException();
        }
        if (messagesRepository.deleteAllMessagesForUser(email)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @PatchMapping(path = Constants.MESSAGES_AS_READ)
    @ResponseStatus(HttpStatus.OK)
    public String messagesAsRead(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BadRequestException();
        }
        if (messagesRepository.messagesAsRead(ids)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }


    private boolean checkEmail(final String email) {
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.isValid(email, null)) {
            return false;
        }
        return true;
    }

}
