package bg.softuni.service;

import bg.softuni.model.service.StoryAddServiceModel;
import bg.softuni.model.view.StoryViewModel;

import java.util.List;
import java.util.Map;

public interface StoryService {
    void addStory(StoryAddServiceModel map) throws Exception;

    Map<String, Integer> availableStoriesInDB();

    List<StoryViewModel> getAllStories();
}
