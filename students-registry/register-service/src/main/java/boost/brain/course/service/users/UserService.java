package boost.brain.course.service.users;

import boost.brain.course.common.users.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserService {

    @Value("${url.host.user}")
    private String host;

    @Value("${url.port.user}")
    private String port;

    @Value("${url.path.user}")
    private String path;


    private RestTemplate template = new RestTemplate();

    public UserDto createUser(UserDto user){
        String url = this.host + ":" + this.port + this.path + "/create";
        UserDto result = template.postForObject(url, user, UserDto.class);
        return result;
    }
}
