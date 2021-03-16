package bg.softuni.web;

import bg.softuni.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("usersCount", userService.getCountOfAllUsersInDB());
        model.addAttribute("loggedUsersCount", userService.getCountOfAllLoggedUsers());
        model.addAttribute("registeredUsersCount", userService.getCountOfAllRegisteredUsers());
        return "statistics";
    }

}
