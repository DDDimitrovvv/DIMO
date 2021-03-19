package bg.softuni.service;

import bg.softuni.model.service.StoryAddServiceModel;

import java.io.IOException;

public interface StoryService {
    void addStory(StoryAddServiceModel map) throws IOException;
}
