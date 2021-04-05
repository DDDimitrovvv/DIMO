package bg.softuni.web;

import bg.softuni.model.binding.UserRegistrationBindingModel;
import bg.softuni.model.service.UserRegistrationServiceModel;
import bg.softuni.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final LogService logService;
    private final ProductService productService;
    private final PurchasedProductService purchasedProductService;
    private final ContactService contactService;
    private final StoryService storyService;

    public UserController(ModelMapper modelMapper,
                          UserService userService,
                          LogService logService,
                          ProductService productService,
                          PurchasedProductService purchasedProductService,
                          ContactService contactService,
                          StoryService storyService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.logService = logService;
        this.productService = productService;
        this.purchasedProductService = purchasedProductService;
        this.contactService = contactService;
        this.storyService = storyService;
    }

    @ModelAttribute("userRegistrationBindingModel")
    public UserRegistrationBindingModel createBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/login")
    public String login() {
//          Error 500
//          throw new NullPointerException();
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAndLoginUser(
            @Valid UserRegistrationBindingModel userRegistrationBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegistrationBindingModel",
                    bindingResult);
            return "redirect:/users/register";
        }

        if (userService.isThisUsernameAlreadyExists(userRegistrationBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("userAlreadyExist", true);

            return "redirect:/users/register";
        }

        UserRegistrationServiceModel userServiceModel = modelMapper.
                map(userRegistrationBindingModel, UserRegistrationServiceModel.class);

        userService.registerAndLoginUser(userServiceModel);
        return "redirect:/home";
    }

    @PostMapping("/login-error")
    public ModelAndView failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                    RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        redirectAttributes.addFlashAttribute("bad_credentials", true);
        redirectAttributes.addFlashAttribute("username", username);
        modelAndView.setViewName("redirect:/users/login");

        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    public String deleteUserById(@PathVariable Long id) throws Exception {

        if(!userService.findUserById(id)){
            return "redirect:/home";
        }

        if(!userService.checkIsAdmin()){
            return "redirect:/home";
        }

        //I. Check if the user has any products and logs
        if (productService.getAllProductsForUser(id).size() > 0) {
            productService.getAllProductsForUser(id).forEach(productViewModel -> {

                //clear all logs for this product (already unused data)
                logService.deleteAllLogsForProductWithId(productViewModel.getId());

                //clear all logs for this product (already unused data)
                productService.deleteProduct(productViewModel.getId());
            });
        }

        //II. Check if the user has any sold or purchased products and delete
        purchasedProductService.deletePurchasedProductByUserEntityId(id);

        //III. Check if the user has any messages
        if (contactService.getAllMessagesFromUser(id).size() > 0) {
            contactService.deleteMessageByUserId(id);
        }

        //IV. Check if the user has any stories
        if (storyService.getAllStoriesByUserId(id).size() > 0) {
            storyService.deleteAllStoriesForUserWithId(id);
        }

        //V. Finally delete the user
        userService.deleteUser(id);

        return "redirect:/admin/statistics";
    }


}
