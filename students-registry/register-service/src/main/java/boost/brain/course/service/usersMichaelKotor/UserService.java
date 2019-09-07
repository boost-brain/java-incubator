package boost.brain.course.service.usersMichaelKotor;

import boost.brain.course.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private static final String SITE_NAME_URL = "http://localhost:8080/";

    private static final String CREATE_USER_URL = SITE_NAME_URL + "api/create";
    //private static final String READ_USER_URL = SITE_NAME_URL + "api/read";
    //private static final String UPDATE_USER_URL = SITE_NAME_URL + "api/update";
    //private static final String DELETE_USER_URL = SITE_NAME_URL + "api/delete";

    private RestTemplate template = new RestTemplate();

    public User createUser(User user){
        User result = template.postForObject(CREATE_USER_URL, user, User.class);
        return result;
    }
}
