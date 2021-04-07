package bg.softuni.components;

import bg.softuni.service.CategoryService;
import bg.softuni.service.ProductService;
import bg.softuni.service.StoryService;
import bg.softuni.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DimoDBApplicationInit implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final StoryService storyService;

    public DimoDBApplicationInit(UserService userService, CategoryService categoryService, ProductService productService, StoryService storyService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.storyService = storyService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();
        categoryService.seedCategories();
        productService.seedProducts();
        storyService.initStories();

    }
}
