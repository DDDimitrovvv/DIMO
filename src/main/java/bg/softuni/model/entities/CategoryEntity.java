package bg.softuni.model.entities;

import bg.softuni.model.entities.enums.CategoryEnum;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    public CategoryEntity() {
    }

    public CategoryEnum getCategoryEnum() {
        return categoryEnum;
    }

    public CategoryEntity setCategoryEnum(CategoryEnum categoryEnum) {
        this.categoryEnum = categoryEnum;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
