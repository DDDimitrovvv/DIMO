package bg.softuni.service.impl;

import bg.softuni.model.entities.StoryEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.enums.StoryTypeEnum;
import bg.softuni.model.service.StoryServiceModel;
import bg.softuni.model.view.StoryViewModel;
import bg.softuni.repository.StoryRepository;
import bg.softuni.service.CloudinaryService;
import bg.softuni.service.StoryService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements StoryService {
    private final ModelMapper modelMapper;
    private final StoryRepository storyRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;


    public StoryServiceImpl(ModelMapper modelMapper, StoryRepository storyRepository, UserService userService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.storyRepository = storyRepository;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void addStory(StoryServiceModel storyServiceModel) throws Exception {

        StoryEntity storyEntity = modelMapper.map(storyServiceModel, StoryEntity.class);
        MultipartFile img = storyServiceModel.getImageUrl();
        String imageUrl = cloudinaryService.uploadImage(img);

        storyEntity.setAddedDate(LocalDate.now()).
                setImageUrl(imageUrl).
                setUserEntity(userService.getCurrentUser());

        storyRepository.save(storyEntity);
    }

    @Override
    public Map<String, Integer> availableStoriesInDB() {

        Map<String, Integer> stories = new LinkedHashMap<>();
        stories.put("storiesCount", storyRepository.findAll().size());

        for ( StoryTypeEnum storyType : StoryTypeEnum.values() ){
            stories.put(storyType.name().toUpperCase(), storyRepository.findAllByStoryTypeEnum(storyType).size());
        }

        return stories;
    }

    @Override
    public List<StoryViewModel> getAllStories() {
        return storyRepository.
                findAll().
                stream().
                map(StoryEntity -> modelMapper.map(StoryEntity, StoryViewModel.class)).
                collect(Collectors.toList());
    }

    @Override
    public StoryViewModel getStoryById(Long id) {
        StoryEntity storyEntity = storyRepository.
                findById(id).
                orElseThrow(() -> new IllegalStateException("Story entity not found."));
        return modelMapper.map(storyEntity, StoryViewModel.class);
    }

    @Override
    public boolean validateUserAccess(Long id) throws Exception {
        UserEntity currentUser = userService.getCurrentUser();
        StoryEntity storyEntity = storyRepository.
                findById(id).
                orElseThrow(() -> new IllegalStateException("Story entity not found."));
        long userIdFromStoryEntity = storyEntity.getUserEntity().getId();
        return currentUser.getId() == userIdFromStoryEntity || currentUser.getUsername().equals("admin@gmail.com");
    }

    @Override
    public void editProduct(StoryServiceModel storyServiceModel, Long id, String notUpdateMyPicture) throws Exception {
        String imageUrl = getStoryById(id).getImageUrl();

        StoryEntity storyEntity = modelMapper.map(storyServiceModel, StoryEntity.class);

        if (notUpdateMyPicture == null) {
            MultipartFile img = storyServiceModel.getImageUrl();
            imageUrl = cloudinaryService.uploadImage(img);
        }

        storyEntity.
                setAddedDate(LocalDate.now()).
                setImageUrl(imageUrl).
                setUserEntity(userService.getCurrentUser()).
                setId(id);

        storyRepository.save(storyEntity);
    }
}
