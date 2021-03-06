package bg.softuni.service;

import bg.softuni.model.service.StoryServiceModel;
import bg.softuni.model.view.StoryViewModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface StoryService {
    void addStory(StoryServiceModel map) throws Exception;

    Map<String, Integer> availableStoriesInDB();

    List<StoryViewModel> getAllStories();

    StoryViewModel getStoryById(Long id);

    boolean validateUserAccess(Long id) throws Exception;

    void editStory(StoryServiceModel storyServiceModel, Long id, String notUpdateMyPicture) throws Exception;

    void deleteStory(Long id);

    List<StoryViewModel> getAllStoriesByCurrUser() throws Exception;

    List<StoryViewModel> getAllStoriesByUserId(Long id);

    void deleteAllStoriesForUserWithId(Long id);

    void initStories();
}
