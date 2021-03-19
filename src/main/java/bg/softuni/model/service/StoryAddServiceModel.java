package bg.softuni.model.service;

import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.enums.StoryTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class StoryAddServiceModel {

    private String id;
    private String title;
    private String description;
    private MultipartFile imageUrl;
    private LocalDate addedDate;
    private String productLink;
    private StoryTypeEnum storyTypeEnum;
    private UserEntity userEntity;


    public StoryAddServiceModel() {
    }

    public String getId() {
        return id;
    }

    public StoryAddServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public StoryAddServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StoryAddServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public StoryAddServiceModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public StoryAddServiceModel setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public String getProductLink() {
        return productLink;
    }

    public StoryAddServiceModel setProductLink(String productLink) {
        this.productLink = productLink;
        return this;
    }

    public StoryTypeEnum getStoryTypeEnum() {
        return storyTypeEnum;
    }

    public StoryAddServiceModel setStoryTypeEnum(StoryTypeEnum storyTypeEnum) {
        this.storyTypeEnum = storyTypeEnum;
        return this;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public StoryAddServiceModel setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }
}
