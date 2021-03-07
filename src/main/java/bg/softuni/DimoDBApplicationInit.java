package bg.softuni;

import bg.softuni.service.CategoryService;
import bg.softuni.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DimoDBApplicationInit implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;

    public DimoDBApplicationInit(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();
        categoryService.seedCategories();
    }
}
