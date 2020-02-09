package boost.brain.course.service.auth;


import boost.brain.course.common.auth.Credentials;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log
@Service
public class AuthService {

    @Value("${url.host.auth}")
    private String host;

    @Value("${url.port.auth}")
    private String port;

    @Value("${url.path.auth}")
    private String path;

//    @Value("${auth_credentials-service-url}")
//    private String url;

    private RestTemplate template = new RestTemplate();

    public boolean createCredentials(Credentials credentials){
        String url = this.host + ":" + this.port + this.path + "/create";
        log.info("url=" + url);
        String result = template.postForObject(url, credentials, String.class);
        if (result.compareTo("true") == 0)
            return true;
        else
            return false;
    }
}
