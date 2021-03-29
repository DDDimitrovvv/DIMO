package bg.softuni.web;

import bg.softuni.model.binding.ProductAddBindingModel;
import bg.softuni.model.binding.ProfileBindingModel;
import bg.softuni.model.service.ProductServiceModel;
import bg.softuni.model.service.ProfileServiceModel;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    public ProfileController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/view")
    public String viewProfile(Model model) {

        model.addAttribute("user", userService.getCurrentUserViewModel());

//          Error 500
//          throw new NullPointerException();
        return "profile";
    }

    @GetMapping("/edit/{id}")
    public String editProfile(Model model, @PathVariable Long id) {

        if (!userService.validateUserAccess(id)) {
            return "redirect:/home";
        }

        model.addAttribute("user", userService.getCurrentUserViewModel());
        return "profile-edit";
    }


    @PatchMapping("/edit/{id}")
    public String editProfileConfirm(@PathVariable Long id,
                                     @Valid ProfileBindingModel profileBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("profileBindingModel", profileBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.profileBindingModel", bindingResult);

            return "redirect:/profile/edit/{id}";
        }
        //update product in DB
        userService.updateUser(modelMapper.map(profileBindingModel, ProfileServiceModel.class), id);

        return "redirect:/profile/view";
    }


}
