package bg.softuni.web;

import bg.softuni.model.binding.ProductAddBindingModel;
import bg.softuni.model.service.ProductAddServiceModel;
import bg.softuni.service.CategoryService;
import bg.softuni.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(CategoryService categoryService, ProductService productService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("productAddBindingModel")) {
            model.addAttribute("productAddBindingModel", new ProductAddBindingModel());
        }
        model.addAttribute("categoryList", categoryService.getListWithAllCategoryNames());

        return "product-add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid ProductAddBindingModel productAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors() || productAddBindingModel.getImageUrl().isEmpty()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productAddBindingModel",
                    bindingResult);

            return "redirect:add";
        }

        //save product in DB
        productService
                .addProduct(modelMapper.map(productAddBindingModel, ProductAddServiceModel.class));

        return "redirect:/home";
    }
}
