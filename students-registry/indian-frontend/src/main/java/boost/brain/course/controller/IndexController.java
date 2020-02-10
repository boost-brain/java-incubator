package boost.brain.course.controller;

import boost.brain.course.Constants;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
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

@Log
@Controller
@RequestMapping(Constants.INDEXCONTROLLER_PREFIX)
public class IndexController {

    private String authUrl;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("actiontext", "Введите Электронную почту и пароль");
        model.addAttribute("credentials", new Credentials());
        return "index";
    }

    @PostMapping(value = "/entering")
    public String entering(Credentials credentials, Model model) {
        log.info("Попытка входа для " + credentials.getLogin());

        Session session;

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpEntity<Credentials> request = new HttpEntity<>(credentials);
        ResponseEntity<Session> responseEntity = restTemplate.postForEntity("http://bjcreslin.ru:8080" +"/credentials"+""+ "/" + "login",
                request, Session.class);

        model.addAttribute("actiontext", responseEntity.getBody().toString());
        return "index";
    }
}
