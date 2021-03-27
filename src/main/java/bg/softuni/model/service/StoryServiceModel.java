package bg.softuni.model.service;

import bg.softuni.model.entities.enums.StoryTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class StoryServiceModel {

    private String title;
    private String description;
    private MultipartFile imageUrl;
    private LocalDate addedDate;
    private String productLink;
    private StoryTypeEnum storyTypeEnum;

    public StoryServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public StoryServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StoryServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public StoryServiceModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public StoryServiceModel setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public String getProductLink() {
        return productLink;
    }

    public StoryServiceModel setProductLink(String productLink) {
        this.productLink = productLink;
        return this;
    }

    public StoryTypeEnum getStoryTypeEnum() {
        return storyTypeEnum;
    }

    public StoryServiceModel setStoryTypeEnum(StoryTypeEnum storyTypeEnum) {
        this.storyTypeEnum = storyTypeEnum;
        return this;
    }
}
