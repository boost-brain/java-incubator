package boost.brain.course.service.auth;


import boost.brain.course.common.auth.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Value("${url.host}")
    private String host;

    @Value("${url.port.auth}")
    private String port;

    @Value("${url.path.auth}")
    private String path;

    private RestTemplate template = new RestTemplate();

    public Credentials createCredentials(Credentials credentials){
        String url = this.host + ":" + this.port + this.path + "/create";
        Credentials result = template.postForObject(url, credentials, Credentials.class);
        return result;
    }
}
