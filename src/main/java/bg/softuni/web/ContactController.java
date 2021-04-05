package bg.softuni.web;

import bg.softuni.model.binding.ContactBindingModel;
import bg.softuni.model.service.ContactServiceModel;
import bg.softuni.service.ContactService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ContactController(ContactService contactService, UserService userService, ModelMapper modelMapper) {
        this.contactService = contactService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/us")
    public String contact(Model model) {

        if (!model.containsAttribute("contactBindingModel")) {
            model.addAttribute("contactBindingModel", new ContactBindingModel());
        }
        return "contact";
    }

    @PostMapping("/us")
    public String contactConfirm(@Valid ContactBindingModel contactBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contactBindingModel", contactBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.contactBindingModel",
                    bindingResult);

            return "redirect:/contact/us";
        }

        contactService.addContactMessage(modelMapper.map(contactBindingModel, ContactServiceModel.class));

        return "redirect:/home";

    }

    @GetMapping("/message/{id}")
    public String viewContactMessage(Model model, @PathVariable Long id) {

        if (!userService.checkIsAdmin()) {
            return "redirect:/home";
        }

        model.addAttribute("messageView", contactService.getMessageById(id));
        return "contact-message-view";
    }

    @GetMapping("/delete/{id}")
    public String deleteContactMessage(Model model, @PathVariable Long id) {

        if (!userService.checkIsAdmin()) {
            return "redirect:/home";
        }
        contactService.deleteMessageByMsgId(id);

        return "redirect:/profile/view";
    }

}
