package bg.softuni.model.entities;

import bg.softuni.model.entities.enums.StoryTypeEnum;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stories")
public class StoryEntity extends BaseEntity {

    @Expose
    @Column(name = "title", nullable = false)
    private String title;

    @Expose
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Expose
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "added_date")
    private LocalDate addedDate;

    @Expose
    @Column(name = "product_link")
    private String productLink;

    @Expose
    @Enumerated(EnumType.STRING)
    private StoryTypeEnum storyTypeEnum;

    @Expose
    @ManyToOne
    private UserEntity userEntity;

    public StoryEntity() {
    }

    public String getTitle() {
        return title;
    }

    public StoryEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public StoryEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public StoryEntity setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public String getProductLink() {
        return productLink;
    }

    public StoryEntity setProductLink(String productLink) {
        this.productLink = productLink;
        return this;
    }

    public StoryTypeEnum getStoryTypeEnum() {
        return storyTypeEnum;
    }

    public StoryEntity setStoryTypeEnum(StoryTypeEnum storyTypeEnum) {
        this.storyTypeEnum = storyTypeEnum;
        return this;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public StoryEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }
}
