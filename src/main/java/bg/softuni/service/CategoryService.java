package bg.softuni.service;

import bg.softuni.model.entities.CategoryEntity;

import java.util.List;

public interface CategoryService {

    void seedCategories();

    List<String> getListWithAllCategoryNames();

    CategoryEntity findCategoryByCategoryName(String categoryName);
}
