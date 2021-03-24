package bg.softuni.web;

import bg.softuni.model.binding.StoryAddBindingModel;
import bg.softuni.model.service.StoryAddServiceModel;
import bg.softuni.service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;
    private final ModelMapper modelMapper;

    public StoryController(StoryService storyService, ModelMapper modelMapper) {
        this.storyService = storyService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("storyAddBindingModel")) {
            model.addAttribute("storyAddBindingModel", new StoryAddBindingModel());
        }

            return "story-add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid StoryAddBindingModel storyAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors() || storyAddBindingModel.getImageUrl().isEmpty()) {
            redirectAttributes.addFlashAttribute("storyAddBindingModel", storyAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.storyAddBindingModel",
                    bindingResult);
            return "redirect:add";
        }

        //save story in DB
        storyService
                .addStory(modelMapper.map(storyAddBindingModel, StoryAddServiceModel.class));

        return "redirect:/home";
    }
}
