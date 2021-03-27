package bg.softuni.web;

import bg.softuni.service.CategoryService;
import bg.softuni.service.ProductService;
import bg.softuni.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) throws Exception {
        model.addAttribute("currentUserFullName", userService.currentUserFullName());
        model.addAttribute("allProducts", productService.getListWithAllProducts());
        model.addAttribute("categoryList", categoryService.getListWithAllCategoryNames());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


}
