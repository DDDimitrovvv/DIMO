package bg.softuni.service;

import bg.softuni.model.service.StoryAddServiceModel;

import java.io.IOException;
import java.util.Map;

public interface StoryService {
    void addStory(StoryAddServiceModel map) throws IOException;

    Map<String, Integer> availableStoriesInDB();

}
