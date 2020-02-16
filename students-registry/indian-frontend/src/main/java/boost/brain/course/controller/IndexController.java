package boost.brain.course.controller;

import boost.brain.course.Constants;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import boost.brain.course.common.register.UserRegDto;
import boost.brain.course.common.users.UserDto;
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
        return "indexreg2";
    }

    @PostMapping(value = "/entering")
    public String entering(Credentials credentials, Model model) {
        log.info("Попытка входа для " + credentials.getLogin());

        ResponseEntity<Session> response = RequestsForOtherServices.getSessionResponseEntityWhenLogin(credentials);
        if (checkResponseForRegistrationAndLoging(response)) {
            doBadEntering(model, credentials, response);
            return "index";
        }
        /***
         * Вход прошёл. доделать
         */
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("actiontext", "Регистрация нового пользователя.");
        model.addAttribute("regdto", new UserRegDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(UserRegDto userRegDto, Model model) {
        log.severe(userRegDto.toString());
        try{
        ResponseEntity<UserDto> response = RequestsForOtherServices.registrationInTheServiceUser(userRegDto);}
        catch (Exception e){
            model.addAttribute("actiontext", "Регистрация прошла неудачно.");
            model.addAttribute("regdto", new UserRegDto());
            model.addAttribute("credentials", new Credentials());
            return "indexreg2";
        }

        model.addAttribute("actiontext", "Регистрация прошла успешно.");
        log.info("Зарегистрирован пользователь " + userRegDto.getEmail());

        ResponseEntity<Boolean> responseAuth = RequestsForOtherServices.registrationInTheServiceAuth(userRegDto);
        log.info("Save auth : " + userRegDto.getEmail());

        return "indexregistr";
    }

    private void doBadRegistration(Model model, UserRegDto userRegDto, ResponseEntity<Session> responseEntity) {
        model.addAttribute("actiontext", "Не удалось зарегистрировать. ");
        log.info("Неверная попытка регистрации пользователя " + userRegDto.getName()
                + " . Код ответа: " + responseEntity.getStatusCode().toString());
        model.addAttribute("credentials", new Credentials());
    }

    /**
     * Проверка ответа на валидность
     */
    private boolean checkResponseForRegistrationAndLoging(ResponseEntity<Session> responseEntity) {
        return Objects.requireNonNull(responseEntity.getBody()).getSessionId() == null;
    }


    /**
     * Ошибка входа
     *
     * @param model model для шаблонизатора
     */
    private void doBadEntering(Model model, Credentials credentials, ResponseEntity<Session> response) {
        model.addAttribute("actiontext", "Пользователь не найден, или введён неверный пароль.");
        log.info("Неверная попытка входа пользователя " + credentials.getLogin());
    }


}
