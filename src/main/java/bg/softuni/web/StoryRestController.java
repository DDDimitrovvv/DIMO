package bg.softuni.web;

import bg.softuni.model.view.StoryViewModel;
import bg.softuni.service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/stories")
@RestController
public class StoryRestController {

    private final StoryService storyService;

    public StoryRestController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/api")
    public ResponseEntity<List<StoryViewModel>> findAll() {
        return ResponseEntity.
                ok().
                body( storyService.getAllStories());
    }

}
