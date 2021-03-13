package bg.softuni.model.entities;

import com.google.gson.annotations.Expose;
import javax.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    @Expose
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Expose
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    public CategoryEntity() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public CategoryEntity setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
