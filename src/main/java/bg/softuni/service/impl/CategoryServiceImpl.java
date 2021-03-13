package bg.softuni.service.impl;

import bg.softuni.model.entities.CategoryEntity;
import bg.softuni.repository.CategoryRepository;
import bg.softuni.service.CategoryService;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final Resource categoriesFile;
    private final Gson gson;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(
            @Value("classpath:init/Hi-Fi.json") Resource categoriesFile,
            Gson gson,
            CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoriesFile = categoriesFile;
        this.gson = gson;
    }

    @Override
    public void seedCategories() {

        if (categoryRepository.count() == 0) {

            try {
                CategoryEntity[] artistEntities =
                        gson.fromJson(Files.
                                readString(Path.of(categoriesFile.getURI())), CategoryEntity[].class);

                Arrays.stream(artistEntities).
                        forEach(categoryRepository::save);
            } catch (IOException e) {
                throw new IllegalStateException("Sorry! The categories cannot be seed in DB!!!");
            }

        }
    }
}
