package boost.brain.course.controller;

import boost.brain.course.Constants;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import boost.brain.course.common.register.UserRegDto;
import boost.brain.course.common.users.UserDto;
import boost.brain.course.dto.UserDtoWithNormalDate;
import boost.brain.course.service.RequestsForOtherServices;
import lombok.extern.java.Log;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log
@Controller
@RequestMapping(Constants.INDEXCONTROLLER_PREFIX)
public class IndexController {
    Session session = null;
    RestTemplate restTemplate;

    public IndexController() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("actiontext", "Введите Электронную почту и пароль");
        model.addAttribute("credentials", new Credentials());
        model.addAttribute("regdto", new UserRegDto());
        return "index";
    }

    @PostMapping(value = "/entering")
    public String entering(Credentials credentials, Model model, HttpServletResponse responseEntering) {
        ResponseEntity<Session> response = RequestsForOtherServices.getSessionResponseEntityWhenLogin(credentials);
        if (checkResponseForRegistrationAndLoging(response)) {
            doBadEntering(model, credentials);
            return "index";
        }
        doGoodEntering(model, credentials);
        /***
         * Вход прошёл. : todo: доделать
         */
        session = response.getBody();
        responseEntering.addHeader("SesionId", Objects.requireNonNull(session).getSessionId());
        return "indexlog";

    }


    /**
     * Проверка - залогинен ли пользователь? Валидна ли его сессия?
     *
     * @return результат ( true- сессия разрешена)
     */
    private boolean checklogin() {
        /**
         * заглушка todo: доделать
         */
        // AUTH_SERVER + Constants.LOGIN_PREFIX +   @GetMapping(path = Constants.CHECK_PREFIX + "/{sessionId}")
        return true;
    }


    @PostMapping("/registration")
    public String registration(UserRegDto userRegDto, Model model) {
        try {
            ResponseEntity<UserDto> response = RequestsForOtherServices.registrationInTheServiceUser(userRegDto);
            ResponseEntity<Boolean> responseAuth = RequestsForOtherServices.registrationInTheServiceAuth(userRegDto);
        } catch (Exception e) {

            doBadRegistration(model, userRegDto);
            return "index";
        }
        doGoodRegistration(model, userRegDto);
        return "indexregistr";
    }


    @GetMapping(Constants.USERCONTROLLER_PREFIX + "showallusers")
    public String showAllUsers(Model model) {
        List<UserDtoWithNormalDate> userDtoList = RequestsForOtherServices.getUserDtoList();
        model.addAttribute("actiontext", "Реестр студентов");
        model.addAttribute("userlist", userDtoList);
        return "showallusers";
    }

    /**
     * Метод, если регистрация прошла неудачно
     */
    private void doBadRegistration(Model model, UserRegDto userRegDto) {
        log.info("Неудачная попытка регистрации пользователя " + userRegDto.getName());
        model.addAttribute("actiontext", "Не удалось зарегистрировать. ");
        model.addAttribute("credentials", new Credentials());
        model.addAttribute("regdto", new UserRegDto());
    }

    /**
     * Метод удачной регистрации
     */
    private void doGoodRegistration(Model model, UserRegDto userRegDto) {
        log.info("Зарегистрирован пользователь " + userRegDto.getEmail());
        model.addAttribute("actiontext", "Регистрация прошла успешно.");
    }


    /**
     * Проверка ответа на валидность
     */
    private boolean checkResponseForRegistrationAndLoging(ResponseEntity<Session> responseEntity) {
        return (responseEntity.getBody() == null)
                || (responseEntity.getBody().getSessionId() == null)
                || (responseEntity.getBody().getSessionId().isEmpty());
    }


    /**
     * Вход выполнен нормально
     */
    private void doGoodEntering(Model model, Credentials credentials) {
        log.info("Пользователь " + credentials.getLogin() + " вошёл в систему.");
    }


    /**
     * Ошибка входа
     *
     * @param model model для шаблонизатора
     */
    private void doBadEntering(Model model, Credentials credentials) {
        model.addAttribute("actiontext", "Пользователь не найден, или введён неверный пароль.");
        model.addAttribute("credentials", credentials);
        model.addAttribute("regdto", new UserRegDto());
        log.info("Неверная попытка входа пользователя " + credentials.getLogin());
    }


}
