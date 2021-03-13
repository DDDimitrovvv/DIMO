package bg.softuni.web;

import bg.softuni.model.binding.UserRegistrationBindingModel;
import bg.softuni.model.service.UserRegistrationServiceModel;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ModelAttribute("userRegistrationBindingModel")
    public UserRegistrationBindingModel createBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/login")
    public String login() {
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

}
