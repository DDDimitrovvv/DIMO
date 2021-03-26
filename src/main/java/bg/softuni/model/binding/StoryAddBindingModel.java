package bg.softuni.model.binding;

import bg.softuni.model.entities.enums.StoryTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StoryAddBindingModel {

    @NotBlank(message = "The title cannot be empty!")
    @Size(min = 3, max = 30, message = "The title must contains between three and twenty characters.")
    private String title;

    @Size(min = 50, message = "The description must be at least five characters.")
    private String description;

    @NotNull(message = "The image cannot be null. Please add it!")
    private MultipartFile imageUrl;

    private String productLink;

    @NotNull(message = "Please select the type of your story!")
    private StoryTypeEnum storyTypeEnum;

    public StoryAddBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public StoryAddBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StoryAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public StoryAddBindingModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getProductLink() {
        return productLink;
    }

    public StoryAddBindingModel setProductLink(String productLink) {
        this.productLink = productLink;
        return this;
    }

    public StoryTypeEnum getStoryTypeEnum() {
        return storyTypeEnum;
    }

    public StoryAddBindingModel setStoryTypeEnum(StoryTypeEnum storyTypeEnum) {
        this.storyTypeEnum = storyTypeEnum;
        return this;
    }
}
