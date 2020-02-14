package boost.brain.course.controller;

import boost.brain.course.Constants;
import boost.brain.course.dto.UserDtoWithNormalDate;
import boost.brain.course.service.RequestsForOtherServices;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log
@Controller
@RequestMapping(Constants.USERCONTROLLER_PREFIX)
public class UserController {

    @GetMapping("showallusers")
    public String showAllUsers(Model model) {
        List<UserDtoWithNormalDate> userDtoList = RequestsForOtherServices.getUserDtoList();
        model.addAttribute("actiontext", "Реестр студентов");
        model.addAttribute("userlist", userDtoList);
        return "showallusers";
    }

}
