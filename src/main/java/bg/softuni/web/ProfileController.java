package bg.softuni.web;

import bg.softuni.model.binding.ProfileBindingModel;
import bg.softuni.model.binding.StoryAddBindingModel;
import bg.softuni.model.binding.UserRegistrationBindingModel;
import bg.softuni.model.service.ProfileServiceModel;
import bg.softuni.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final ProductService productService;
    private final StoryService storyService;
    private final PurchasedUserService purchasedUserService;
    private final PurchasedProductService purchasedProductService;
    private final ModelMapper modelMapper;


    public ProfileController(UserService userService,
                             ProductService productService,
                             StoryService storyService,
                             PurchasedUserService purchasedUserService,
                             PurchasedProductService purchasedProductService,
                             ModelMapper modelMapper) {
        this.userService = userService;
        this.productService = productService;
        this.storyService = storyService;
        this.purchasedUserService = purchasedUserService;
        this.purchasedProductService = purchasedProductService;
        this.modelMapper = modelMapper;
    }


    @ModelAttribute("profileBindingModel")
    public ProfileBindingModel createBindingModel() {
        return new ProfileBindingModel();
    }


    @GetMapping("/view")
    public String viewProfile(Model model) throws Exception {
        System.out.println();

        model.addAttribute("user", userService.getCurrentUserViewModel());
        model.addAttribute("userProductsList", productService.getAllProductsForCurrUser());
        model.addAttribute("userStoriesList", storyService.getAllStoriesByCurrUser());
//        model.addAttribute("userSoldProductsList", purchasedProductService.getAllProductSoldByUserId());
//        model.addAttribute("UserPurchasedProductsList", purchasedUserService.getAllPurchasedProductsByThisUser());
        System.out.println();



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

        if (userService.isThisUsernameAlreadyExists(profileBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("profileBindingModel", profileBindingModel);
            redirectAttributes.addFlashAttribute("userAlreadyExist", true);
            return "redirect:/profile/edit/{id}";
        }

        ProfileServiceModel profileServiceModel = modelMapper.map(profileBindingModel, ProfileServiceModel.class);

        //update product in DB
        userService.updateUser(profileServiceModel, id);

        return "redirect:/profile/view";
    }


}
