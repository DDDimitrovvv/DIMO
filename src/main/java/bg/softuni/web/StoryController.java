package bg.softuni.web;

import bg.softuni.model.binding.StoryAddBindingModel;
import bg.softuni.model.service.StoryServiceModel;
import bg.softuni.model.view.StoryViewModel;
import bg.softuni.service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/all")
    public String allStories(Model model) {
        model.addAttribute("storiesCollection", storyService.getAllStories());

        return "stories";
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
                .addStory(modelMapper.map(storyAddBindingModel, StoryServiceModel.class));

        return "redirect:/stories/all";
    }


    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id) throws Exception {
        model.addAttribute("story", storyService.getStoryById(id));
        model.addAttribute("editAccess", storyService.validateUserAccess(id));


        return "story-details";
    }

    @GetMapping("/edit/{id}")
    public String editStory(Model model, @PathVariable Long id) throws Exception {

        if (!storyService.validateUserAccess(id)) {
            return "redirect:/home";
        }
        StoryViewModel storyViewModel = storyService.getStoryById(id);
        model.addAttribute("storyModel", storyViewModel);

        return "story-edit";
    }

    @PatchMapping("/edit/{id}")
    public String editStoryConfirm(@PathVariable Long id,
                                   @Valid StoryAddBindingModel storyAddBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes, String notUpdateMyPicture) throws Exception {

        if (bindingResult.hasErrors() || notUpdateMyPicture == null && storyAddBindingModel.getImageUrl().isEmpty()) {
            redirectAttributes.addFlashAttribute("storyAddBindingModel", storyAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.storyAddBindingModel", bindingResult);

            return "redirect:/stories/edit/{id}";
        }
        //update product in DB
        storyService
                .editProduct(modelMapper.map(storyAddBindingModel, StoryServiceModel.class), id, notUpdateMyPicture);

        return "redirect:/stories/details/{id}";
    }


    @GetMapping("/delete/{id}")
    public String deleteId(@PathVariable Long id){
        storyService.deleteStory(id);
        return "redirect:/stories/all";
    }


}
