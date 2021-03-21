package bg.softuni.model.view;

import bg.softuni.model.entities.enums.StoryTypeEnum;

import java.time.LocalDate;

public class StoryViewModel {

    private long id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDate addedDate;
    private String productLink;
    private StoryTypeEnum storyTypeEnum;

    public StoryViewModel() {
    }

    public long getId() {
        return id;
    }

    public StoryViewModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public StoryViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StoryViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public StoryViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public StoryViewModel setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public String getProductLink() {
        return productLink;
    }

    public StoryViewModel setProductLink(String productLink) {
        this.productLink = productLink;
        return this;
    }

    public StoryTypeEnum getStoryTypeEnum() {
        return storyTypeEnum;
    }

    public StoryViewModel setStoryTypeEnum(StoryTypeEnum storyTypeEnum) {
        this.storyTypeEnum = storyTypeEnum;
        return this;
    }
}
