package bg.softuni.repository;

import bg.softuni.model.entities.CategoryEntity;
import bg.softuni.model.entities.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findCategoryEntityByCategoryEnum(CategoryEnum categoryEnum);
}
