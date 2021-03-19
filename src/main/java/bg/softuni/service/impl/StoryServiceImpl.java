package bg.softuni.service.impl;

import bg.softuni.model.entities.StoryEntity;
import bg.softuni.model.service.StoryAddServiceModel;
import bg.softuni.repository.StoryRepository;
import bg.softuni.service.CloudinaryService;
import bg.softuni.service.StoryService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

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
    public void addStory(StoryAddServiceModel storyAddServiceModel) throws IOException {

        StoryEntity storyEntity = modelMapper.map(storyAddServiceModel, StoryEntity.class);
        MultipartFile img = storyAddServiceModel.getImageUrl();
        String imageUrl = cloudinaryService.uploadImage(img);

        storyEntity.setAddedDate(LocalDate.now()).
                setImageUrl(imageUrl).
                setUserEntity(userService.getCurrentUser());

        storyRepository.save(storyEntity);
    }
}
