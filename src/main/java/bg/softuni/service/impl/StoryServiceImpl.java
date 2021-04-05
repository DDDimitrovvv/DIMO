package bg.softuni.service.impl;

import bg.softuni.model.entities.StoryEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.UserRoleEntity;
import bg.softuni.model.entities.enums.StoryTypeEnum;
import bg.softuni.model.entities.enums.UserRole;
import bg.softuni.model.service.StoryServiceModel;
import bg.softuni.model.view.ProductViewModel;
import bg.softuni.model.view.StoryViewModel;
import bg.softuni.repository.StoryRepository;
import bg.softuni.service.CloudinaryService;
import bg.softuni.service.StoryService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
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
                orElse(null);
//                orElseThrow(() -> new IllegalStateException("Story entity not found."));
        if (storyEntity != null) {
            return modelMapper.map(storyEntity, StoryViewModel.class);
        }
        return new StoryViewModel();
    }

    @Override
    public boolean validateUserAccess(Long id) throws Exception {
        UserEntity currentUser = userService.getCurrentUser();
        StoryEntity storyEntity = storyRepository.
                findById(id).
                orElseThrow(() -> new IllegalStateException("Story entity not found."));
        long userIdFromStoryEntity = storyEntity.getUserEntity().getId();
        UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);

        boolean isAdmin = false;
        for ( UserRoleEntity role : currentUser.getRoles() ){
            if (role.getRole().equals(UserRole.ADMIN)) {
                isAdmin = true;
                break;
            }
        }
        return currentUser.getId() == userIdFromStoryEntity || isAdmin;
    }

    @Override
    public void editStory(StoryServiceModel storyServiceModel, Long id, String notUpdateMyPicture) throws Exception {
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

    @Override
    public void deleteStory(Long id) {
        storyRepository.deleteById(id);
    }

    @Override
    public List<StoryViewModel> getAllStoriesByCurrUser() throws Exception {
        List<StoryViewModel> allStoriesView = new ArrayList<>();
        if (storyRepository.findAllByUserEntity_Id(userService.getCurrentUser().getId()).size() > 0) {
            allStoriesView.addAll(storyRepository.
                    findAllByUserEntity_Id(userService.getCurrentUser().getId()).
                    stream().
                    map(storyEntity -> modelMapper.map(storyEntity, StoryViewModel.class)).
                    collect(Collectors.toList()));
        }
        return allStoriesView;
    }

    @Override
    public List<StoryViewModel> getAllStoriesByUserId(Long id) {
        List<StoryViewModel> allStoriesView = new ArrayList<>();
        if (storyRepository.findAllByUserEntity_Id(id).size() > 0) {
            allStoriesView.addAll(storyRepository.
                    findAllByUserEntity_Id(id).
                    stream().
                    map(storyEntity -> modelMapper.map(storyEntity, StoryViewModel.class)).
                    collect(Collectors.toList()));
        }
        return allStoriesView;
    }

    @Override
    public void deleteAllStoriesForUserWithId(Long id) {
        for ( StoryEntity storyEntity : storyRepository.findAllByUserEntity_Id(id) ){
            storyRepository.deleteById(storyEntity.getId());
        }
    }
}
