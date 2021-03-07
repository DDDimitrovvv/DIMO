package bg.softuni.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "stories")
public class StoryEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "addedDate")
    private LocalDate addedDate;

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

    public String getLink() {
        return link;
    }

    public StoryEntity setLink(String link) {
        this.link = link;
        return this;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public StoryEntity setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }
}
