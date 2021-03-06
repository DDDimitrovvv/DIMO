package bg.softuni.web;

import bg.softuni.model.binding.ProductAddBindingModel;
import bg.softuni.model.service.ProductServiceModel;
import bg.softuni.model.view.ProductViewModel;
import bg.softuni.service.CategoryService;
import bg.softuni.service.LogService;
import bg.softuni.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final LogService logService;

    public ProductController(CategoryService categoryService, ProductService productService, ModelMapper modelMapper, LogService logService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.logService = logService;
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
                .addProduct(modelMapper.map(productAddBindingModel, ProductServiceModel.class));

        return "redirect:/home";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id) throws Exception {
        try {
            model.addAttribute("product", productService.findById(id));
            model.addAttribute("creator", productService.amITheCreatorOfThisProduct(id));
            model.addAttribute("editAccess", productService.validateUserAccess(id));

            return "product-details";
        } catch (IllegalArgumentException exception) {
            return "redirect:/home";
        }
    }


    @GetMapping("/archived/{id}")
    public String purchasedProduct(Model model, @PathVariable Long id) throws Exception {
        ProductViewModel productViewModel = productService.findArchivedProductById(id);
        model.addAttribute("archivedProduct", productViewModel);

        return "archived_product-details";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable Long id) throws Exception {

        if (!productService.validateUserAccess(id)) {
            return "redirect:/home";
        }

        ProductViewModel productViewModel = productService.findById(id);
        model.addAttribute("productViewModel", productViewModel);
        model.addAttribute("categoryListItems", categoryService.getListWithAllCategoryNames());

        return "product-edit";
    }

    @PatchMapping("/edit/{id}")
    public String editProductConfirm(@PathVariable Long id,
                                     @Valid ProductAddBindingModel productAddBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, String notUpdateMyPicture) throws Exception {

        if (bindingResult.hasErrors() || notUpdateMyPicture == null && productAddBindingModel.getImageUrl().isEmpty()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:/products/edit/{id}";
        }
        //update product in DB
        productService
                .editProduct(modelMapper.map(productAddBindingModel, ProductServiceModel.class), id, notUpdateMyPicture);

        return "redirect:/products/details/{id}";
    }


    @GetMapping("/delete/{id}")
    public String deleteId(@PathVariable Long id) {

        //clear all logs for this product (already unused data)
        logService.deleteAllLogsForProductWithId(id);

        //clear all logs for this product (already unused data)
        productService.deleteProduct(id);

        return "redirect:/home";
    }


    @GetMapping("/buy/{id}")
    public String buyProduct(@PathVariable Long id) throws Exception {

        logService.deleteAllLogsForProductWithId(id);
        productService.buyProduct(id);

        return "redirect:/home";
    }


}
