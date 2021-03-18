package bg.softuni.service.impl;

import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.service.ProductAddServiceModel;
import bg.softuni.model.view.ProductViewModel;
import bg.softuni.repository.ProductRepository;
import bg.softuni.service.CategoryService;
import bg.softuni.service.CloudinaryService;
import bg.softuni.service.ProductService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final UserService userService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, CategoryService categoryService, UserService userService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    public List<ProductViewModel> getListWithAllProducts() {
        return productRepository.
                findAll().
                stream().
                map(productEntity -> modelMapper.map(productEntity, ProductViewModel.class)).
                collect(Collectors.toList());
    }

    @Override
    public void addProduct(ProductAddServiceModel productAddServiceModel) throws IOException {

        ProductEntity productEntity = modelMapper.map(productAddServiceModel, ProductEntity.class);

        MultipartFile img = productAddServiceModel.getImageUrl();
        String imageUrl = cloudinaryService.uploadImage(img);

        productEntity.
                setImageUrl(imageUrl).
                setCategoryEntity(categoryService.findCategoryByCategoryName(productAddServiceModel.getCategoryName())).
                setUserEntity(userService.getCurrentUser());

        System.out.println();
        productRepository.save(productEntity);
    }
}
