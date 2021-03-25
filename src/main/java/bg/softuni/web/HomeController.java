package bg.softuni.web;

import bg.softuni.service.ProductService;
import bg.softuni.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final UserService userService;
    private final ProductService productService;

    public HomeController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) throws Exception {
        model.addAttribute("currentUserFullName", userService.currentUserFullName());
        model.addAttribute("allProducts", productService.getListWithAllProducts());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


}
