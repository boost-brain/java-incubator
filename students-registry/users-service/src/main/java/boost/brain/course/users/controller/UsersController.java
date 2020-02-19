package boost.brain.course.users.controller;

import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;
import boost.brain.course.users.Constants;

import boost.brain.course.users.controller.exceptions.BadRequestException;
import boost.brain.course.users.controller.exceptions.ConflictException;
import boost.brain.course.users.controller.exceptions.NotFoundException;
import boost.brain.course.users.repository.UsersRepository;
import lombok.extern.java.Log;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Log
@RestController
@RequestMapping(Constants.USERS_CONTROLLER_PREFIX)
public class UsersController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @PostMapping(path = Constants.CREATE_PREFIX,
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDto create(@RequestBody UserDto userDto) {
        if (StringUtils.isEmpty(userDto.getEmail()) ||
                !this.checkEmail(userDto.getEmail()) ||
                StringUtils.isEmpty(userDto.getGitHubId()) ||
                StringUtils.isEmpty(userDto.getName()) ||
                (userDto.getHours() < 1)) {
            throw new BadRequestException();
        }
        userDto.setStatus(UserStatus.BUSY);
        long time = System.currentTimeMillis();
        userDto.setCreateDate(time);
        userDto.setUpdateDate(time);
        UserDto result = usersRepository.create(userDto);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{email}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDto read(@PathVariable String email, HttpServletResponse httpServletResponse) {
        if (StringUtils.isEmpty(email) || !this.checkEmail(email)) {
            throw new NotFoundException();
        }
        UserDto result = usersRepository.read(email);
        if (result == null) {
            throw new NotFoundException();
        }
        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");
        return result;
    }

    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody UserDto userDto) {
        if (StringUtils.isEmpty(userDto.getEmail()) ||
                !this.checkEmail(userDto.getEmail()) ||
                StringUtils.isEmpty(userDto.getGitHubId()) ||
                StringUtils.isEmpty(userDto.getName()) ||
                (userDto.getStatus() == null) ||
                (userDto.getHours() < 1)) {
            throw new BadRequestException();
        }
        userDto.setUpdateDate(System.currentTimeMillis());
        if (usersRepository.update(userDto)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @PutMapping(path = Constants.PUT_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String updatePut(@RequestBody UserDto userDto) {
        if (StringUtils.isEmpty(userDto.getEmail()) ||
                !this.checkEmail(userDto.getEmail()) ||
                StringUtils.isEmpty(userDto.getGitHubId()) ||
                StringUtils.isEmpty(userDto.getName()) ||
                (userDto.getStatus() == null) ||
                (userDto.getHours() < 1)) {
            throw new BadRequestException();
        }
        userDto.setUpdateDate(System.currentTimeMillis());
        if (usersRepository.update(userDto)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{email}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable final String email) {
        if (StringUtils.isEmpty(email) || !this.checkEmail(email)) {
            throw new BadRequestException();
        }
        if (usersRepository.delete(email)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(path = Constants.COUNT_PREFIX,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody long count() {
        return usersRepository.count();
    }

    @GetMapping(path = Constants.PAGE_PREFIX + "/{page}/{size}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserDto> page(@PathVariable int page, @PathVariable int size) {
        if (page < 1 || size < 1) {
            throw new BadRequestException();
        }
        List<UserDto> result = usersRepository.getPage(page,size);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @PostMapping(path = Constants.USERS_FOR_EMAILS_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserDto> usersForEmails(@RequestBody List<String> emails) {
        if (emails == null || emails.isEmpty()) {
            throw new BadRequestException();
        }
        List<UserDto> result = usersRepository.usersForEmails(emails);
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @GetMapping(path = Constants.USERS_ALL_PREFIX,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserDto> allUsers() {
        List<UserDto> result = usersRepository.allUsers();
        if (result == null) {
            throw new ConflictException();
        }
        return result;
    }

    @GetMapping(path = Constants.CHECK_IF_EXISTS_PREFIX + "/{email}")
    public boolean checkIfExists(@PathVariable String email) {
        if (StringUtils.isEmpty(email) || !this.checkEmail(email)) {
            throw new BadRequestException();
        }
        return usersRepository.checkIfExists(email);
    }

    @PostMapping(path = Constants.UPDATE_STATUS_PREFIX + "/{email}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String updateStatus(@PathVariable String email,
                               @RequestBody UserStatus status) {
        if (StringUtils.isEmpty(email) || !this.checkEmail(email) || (status == null)) {
            throw new BadRequestException();
        }
        UserDto userDto = usersRepository.read(email);
        if (userDto == null) {
            throw new NotFoundException();
        }
        userDto.setUpdateDate(System.currentTimeMillis());
        userDto.setStatus(status);
        if (usersRepository.updateStatus(userDto)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new ConflictException();
        }
    }

    @PostMapping(path = Constants.UPDATE_STATUSES_FOR_EMAILS_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String updateStatusesForEmails(@RequestBody Map<String, UserStatus> emailsWithStatusesMap) {
        log.info("Start UsersController.updateStatusesForEmails");
        if (emailsWithStatusesMap == null || emailsWithStatusesMap.isEmpty()) {
            throw new BadRequestException();
        }
        if (usersRepository.updateStatusesForEmails(emailsWithStatusesMap)) {
            log.info("Finish UsersController.updateStatusesForEmails");
            return HttpStatus.OK.getReasonPhrase();
        } else {
            log.severe("Throw ConflictException in UsersController.updateStatusesForEmails");
            throw new ConflictException();
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
