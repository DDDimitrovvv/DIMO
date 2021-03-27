package bg.softuni.service.impl;

import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.service.ProductServiceModel;
import bg.softuni.model.view.ProductViewModel;
import bg.softuni.repository.CategoryRepository;
import bg.softuni.repository.ProductRepository;
import bg.softuni.service.CategoryService;
import bg.softuni.service.CloudinaryService;
import bg.softuni.service.ProductService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              ModelMapper modelMapper,
                              CloudinaryService cloudinaryService,
                              CategoryService categoryService,
                              UserService userService,
                              CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
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
    public void addProduct(ProductServiceModel productServiceModel) throws Exception {

        ProductEntity productEntity = modelMapper.map(productServiceModel, ProductEntity.class);
        MultipartFile img = productServiceModel.getImageUrl();
        String imageUrl = cloudinaryService.uploadImage(img);

        productEntity.
                setImageUrl(imageUrl).
                setCategoryEntity(categoryService.findCategoryByCategoryName(productServiceModel.getCategoryName())).
                setUserEntity(userService.getCurrentUser());

        productRepository.save(productEntity);
    }

    @Override
    public Map<String, Integer> availableProductInDB() {

        Map<String, Integer> products = new LinkedHashMap<>();
        products.put("productCount", productRepository.findAll().size());

        categoryRepository.findAll().forEach(category -> {
            products.put(category.getCategoryName(), productRepository.findAllByCategoryEntity_CategoryName(category.getCategoryName()).size());
        });
        return products;
    }

    @Override
    public ProductViewModel findById(Long id) {
        return productRepository.
                findById(id).
                map(productEntity -> {
                    ProductViewModel productViewModel = modelMapper.map(productEntity, ProductViewModel.class);
                    productViewModel.setCategoryName(productEntity.getCategoryEntity().getCategoryName());
                    return productViewModel;
                }).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public ProductEntity findProductEntityById(Long productId) {
        return productRepository.
                findById(productId).
                orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean validateUserAccess(Long id) throws Exception {
        UserEntity currentUser = userService.getCurrentUser();
        long userIdFromProductEntity = findProductEntityById(id).getUserEntity().getId();
        return currentUser.getId() == userIdFromProductEntity || currentUser.getUsername().equals("admin@gmail.com");
    }

    @Override
    public void editProduct(ProductServiceModel productServiceModel, Long id, String notUpdateMyPicture) throws Exception {
        String imageUrl = findById(id).getImageUrl();

        ProductEntity productEntity = modelMapper.map(productServiceModel, ProductEntity.class);

        if (notUpdateMyPicture == null) {
            MultipartFile img = productServiceModel.getImageUrl();
            imageUrl = cloudinaryService.uploadImage(img);
        }

        productEntity.
                setImageUrl(imageUrl).
                setCategoryEntity(categoryService.findCategoryByCategoryName(productServiceModel.getCategoryName())).
                setUserEntity(userService.getCurrentUser()).
                setId(id);

        productRepository.save(productEntity);
    }
}
