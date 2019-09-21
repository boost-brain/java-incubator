package boost.brain.course.frontend.users.beans;

import boost.brain.course.frontend.users.model.User;
import boost.brain.course.frontend.users.view.UserService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Data
@Log
@RequestScope
@Named
public class UserBean implements Serializable {

    private List<User> users;

    private UserService userService;

    @PostConstruct
    public void init() {
        this.users = userService.getAllUsers();
    }

    public void setService(UserService userService) {
        this.userService = userService;
    }

    public UserBean() {
    }

    @Autowired
    public UserBean(UserService userService) {
        this.userService = userService;
    }
}
