package boost.brain.course.frontend.users.view;

import boost.brain.course.common.users.UserDto;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Log
@Service
public class UserService {

    @Value("${users-service-url}")
    private String userUrl;


    public List<UserDto> getAllUsers() {
        RestTemplate restTemplate = new RestTemplate();

        log.info(userUrl);

        Long usersRowCount = restTemplate.getForObject(userUrl + "/count", Long.class);

        log.info("Count " + usersRowCount);

        if (usersRowCount != null && usersRowCount != 0 && usersRowCount >= 0) {
            ResponseEntity<List<UserDto>> responseEntity = restTemplate.exchange(userUrl + "/page/1/" + usersRowCount.toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserDto>>() {
                    });

            return responseEntity.getBody();

        }

        return Collections.emptyList();
    }
}
