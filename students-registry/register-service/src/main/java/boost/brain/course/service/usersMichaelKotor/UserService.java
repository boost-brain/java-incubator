package boost.brain.course.service.usersMichaelKotor;

import boost.brain.course.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Value("${url.host}")
    private String host;

    @Value("${url.port.user}")
    private String port;

    @Value("${url.path.user}")
    private String path;

//    private String CREATE_USER_URL = host + ":" + port + path + "/create";
    //private static final String READ_USER_URL = SITE_NAME_URL + "api/read";
    //private static final String UPDATE_USER_URL = SITE_NAME_URL + "api/update";
    //private static final String DELETE_USER_URL = SITE_NAME_URL + "api/delete";

    private RestTemplate template = new RestTemplate();

    public User createUser(User user){
        String url = this.host + ":" + this.port + this.path + "/create";
        User result = template.postForObject(url, user, User.class);
        return result;
    }
}
