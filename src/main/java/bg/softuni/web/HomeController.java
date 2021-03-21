package bg.softuni.web;

import bg.softuni.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) throws Exception {
        model.addAttribute("currentUserFullName", userService.currentUserFullName());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


}
