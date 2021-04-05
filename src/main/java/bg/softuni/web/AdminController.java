package bg.softuni.web;

import bg.softuni.service.LogService;
import bg.softuni.service.ProductService;
import bg.softuni.service.StoryService;
import bg.softuni.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final StoryService storyService;
    private final LogService logService;

    public AdminController(UserService userService,
                           ProductService productService,
                           StoryService storyService,
                           LogService logService) {
        this.userService = userService;
        this.productService = productService;
        this.storyService = storyService;
        this.logService = logService;
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {

        if (!userService.checkIsAdmin()) {
            return "redirect:/home";
        }

        model.addAttribute("usersCount", userService.getCountOfAllUsersInDB());
        model.addAttribute("loggedUsersCount", userService.getCountOfAllLoggedUsers());
        model.addAttribute("registeredUsersCount", userService.getCountOfAllRegisteredUsers());

        Map<String, Integer> products = productService.availableProductInDB();
        model.addAttribute("totalProductCount", products.get("productCount"));
        model.addAttribute("totalSpeakerProductCount", products.get("Speakers"));
        model.addAttribute("totalReceiverProductCount", products.get("Receivers"));
        model.addAttribute("totalAccessoriesProductCount", products.get("Accessories"));
        model.addAttribute("totalHome_CinemaProductCount", products.get("Home_Cinema"));

        Map<String, Integer> stories = storyService.availableStoriesInDB();
        model.addAttribute("totalStoriesCount", stories.get("storiesCount"));
        model.addAttribute("totalFunStoriesCount", stories.get("FUN"));
        model.addAttribute("totalIssueStoriesCount", stories.get("ISSUE"));
        model.addAttribute("totalInfoStoriesCount", stories.get("INFO"));

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.ENGLISH);
        model.addAttribute("localDate", LocalDate.now().format(formatter));

        model.addAttribute("isAvailableUserChoice", logService.getAllLogsOfUsers().size() > 0);
        model.addAttribute("userChoice", logService.getAllLogsOfUsers());

        return "statistics";
    }

    @GetMapping("/roles")
    public String roles(Model model) throws Exception {
        if (!userService.checkIsAdmin()) {
            return "redirect:/home";
        }
        model.addAttribute("usernameList", userService.getAllUsernameList());
        return "roles";
    }

    @GetMapping("/users")
    public String users(Model model) throws Exception {
        if (!userService.checkIsAdmin()) {
            return "redirect:/home";
        }
        model.addAttribute("users", userService.getAllUsersFromDB());
        return "users";
    }

    @PostMapping("/roles")
    public String rolesConfirm(String username, String role) throws Exception {
        userService.changeRole(username, role);
        return "redirect:/home";
    }
}
