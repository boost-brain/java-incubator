package boost.brain.course.service.accountArkasandr;

import boost.brain.course.model.Account;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {

    private static final String SITE_NAME_URL = "http://localhost:8080/api/account";

    private static final String CREATE_ACCOUNT_URL = SITE_NAME_URL + "/create";

    private RestTemplate template = new RestTemplate();

    public Account createAccount(Account account){
        Account result = template.postForObject(CREATE_ACCOUNT_URL, account, Account.class);
        return result;
    }
}
