package boost.brain.course.service.users;

import boost.brain.course.common.auth.UserDto;
import boost.brain.course.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserService {

    @Value("${url.host}")
    private String host;

    @Value("${url.port.user}")
    private String port;

    @Value("${url.path.user}")
    private String path;


    private RestTemplate template = new RestTemplate();

    public User createUser(User user){
        String url = this.host + ":" + this.port + this.path + "/create";
        User result = template.postForObject(url, user, User.class);
        return result;
    }
}
