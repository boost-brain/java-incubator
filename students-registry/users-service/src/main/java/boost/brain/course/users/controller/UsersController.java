package boost.brain.course.users.controller;

import boost.brain.course.users.Constants;
import boost.brain.course.users.controller.dto.UserDto;
import boost.brain.course.users.controller.exceptions.NotFoundException;
import boost.brain.course.users.repository.UsersRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                StringUtils.isEmpty(userDto.getGitHabId()) ||
                StringUtils.isEmpty(userDto.getName()) ||
                (userDto.getHours() < 1)) {
            throw new NotFoundException();
        }
        long time = System.currentTimeMillis();
        userDto.setCreateDate(time);
        userDto.setUpdateDate(time);
        UserDto result = usersRepository.create(userDto);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{email}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDto read(@PathVariable String email) {
        if (StringUtils.isEmpty(email) || !this.checkEmail(email)) {
            throw new NotFoundException();
        }
        UserDto result = usersRepository.read(email);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody UserDto userDto) {
        if (StringUtils.isEmpty(userDto.getEmail()) ||
                !this.checkEmail(userDto.getEmail()) ||
                StringUtils.isEmpty(userDto.getGitHabId()) ||
                StringUtils.isEmpty(userDto.getName()) ||
                (userDto.getHours() < 1)) {
            throw new NotFoundException();
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
            throw new NotFoundException();
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
            throw new NotFoundException();
        }
        List<UserDto> result = usersRepository.getPage(page,size);
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
