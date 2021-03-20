package bg.softuni.web;

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

    public AdminController(UserService userService,
                           ProductService productService,
                           StoryService storyService) {
        this.userService = userService;
        this.productService = productService;
        this.storyService = storyService;
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
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

        return "statistics";
    }

    @GetMapping("/roles")
    public String roles(Model model) {
        model.addAttribute("usernameList", userService.getAllUsernameList());
        return "roles";
    }

    @PostMapping("/roles")
    public String rolesConfirm( String username,
                              String role) {

        userService.changeRole(username, role);

        return "redirect:/home";
    }
}
