package boost.brain.course.controller;

import boost.brain.course.Constants;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import boost.brain.course.common.register.UserRegDto;
import lombok.extern.java.Log;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

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
        return "index";
    }

    @PostMapping(value = "/entering")
    public String entering(Credentials credentials, Model model) {
        log.info("Попытка входа для " + credentials.getLogin());

        HttpEntity<Credentials> request = new HttpEntity<>(credentials);
        ResponseEntity<Session> response = restTemplate.postForEntity(Constants.AUTH_SERVER + "api/login/login",
                request, Session.class);
        if (checkResponseForRegistrationAndLoging(response)) {
            doBadEntering(model, request, response);
            return "index";
        }
        /***
         * Вход прошёл. доделать
         */
        return "index";
    }

    /**
     * Ошибка входа
     *
     * @param model   model для шаблонизатора
     * @param request запрос
     */
    private void doBadEntering(Model model, HttpEntity<Credentials> request, ResponseEntity<Session> response) {
        model.addAttribute("actiontext", "Пользователь не найден, или введён неверный пароль.");
        log.info("Неверная попытка входа пользователя " + Objects.requireNonNull(request.getBody()).getLogin());
    }

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("actiontext", "Регистрация нового пользователя.");
        model.addAttribute("regdto", new UserRegDto());
        return "registration";
    }

    @PostMapping("registration")
    public String registration(UserRegDto userRegDto, Model model) {
        HttpEntity<UserRegDto> userRegDtoRequest = new HttpEntity<>(userRegDto);
        ResponseEntity<Session> response = restTemplate.postForEntity(Constants.USER_SERVER + "api/users/create",
                userRegDtoRequest, Session.class);
        if (checkResponseForRegistrationAndLoging(response)) {
            doBadRegistration(model, userRegDtoRequest, response);
            return "index";
        }
        model.addAttribute("actiontext", "Регистрация прошла успешно.");
        log.info("Зарегистрирован пользователь " + Objects.requireNonNull(userRegDtoRequest.getBody()).getEmail());

        Credentials credentials = getCredentialsFromUserReg(userRegDto);
        HttpEntity<Credentials> credentialRequest = new HttpEntity<>(credentials);
        response = restTemplate.postForEntity(Constants.AUTH_SERVER + "/api/credentials/create",
                credentialRequest, Session.class);
        session = response.getBody();
        model.addAttribute("credentials", credentials);
        return "index";
    }

    private void doBadRegistration(Model model, HttpEntity<UserRegDto> request, ResponseEntity<Session> responseEntity) {
        model.addAttribute("actiontext", "Не удалось зарегистрировать. ");
        log.info("Неверная попытка регистрации пользователя " + Objects.requireNonNull(request.getBody()).getEmail()
                + "код:" + responseEntity.getStatusCode().toString());
        model.addAttribute("credentials", new Credentials());
    }

    /**
     * Проверка ответа на валидность
     *
     * @param responseEntity
     * @return
     */
    private boolean checkResponseForRegistrationAndLoging(ResponseEntity<Session> responseEntity) {
        return Objects.requireNonNull(responseEntity.getBody()).getSessionId() == null;
    }

    /**
     * Делает из userDto объект класса Credentials
     *
     * @param userRegDto объект
     * @return объект
     */
    private Credentials getCredentialsFromUserReg(UserRegDto userRegDto) {
        Credentials credentials = new Credentials();
        credentials.setLogin(userRegDto.getEmail());
        credentials.setPassword(userRegDto.getPassword());
        return credentials;
    }

    @GetMapping("allusers")
    public String showAllUsers(Model model) {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(Constants.USER_SERVER + "api/users/users-all",
                List.class);
        List userDtoList = responseEntity.getBody();
        model.addAttribute("actiontext", "Реестр студентов");
        model.addAttribute("userlist", userDtoList);
        return "showallusers";
    }


}
