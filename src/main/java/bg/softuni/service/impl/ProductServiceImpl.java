package bg.softuni.service.impl;

import bg.softuni.model.entities.ArchivedProductEntity;
import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.service.ProductServiceModel;
import bg.softuni.model.view.ProductViewModel;
import bg.softuni.repository.CategoryRepository;
import bg.softuni.repository.ProductRepository;
import bg.softuni.service.*;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final PurchasedProductService purchasedProductService;
    private final Gson gson;
    private final Resource productsFile;


    public ProductServiceImpl(@Value("classpath:init/products.json") Resource productsFile,
                              ProductRepository productRepository,
                              ModelMapper modelMapper,
                              CloudinaryService cloudinaryService,
                              CategoryService categoryService,
                              UserService userService,
                              CategoryRepository categoryRepository,
                              PurchasedProductService purchasedProductService,
                              Gson gson) {

        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.purchasedProductService = purchasedProductService;
        this.gson = gson;
        this.productsFile = productsFile;
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
                orElse(null);
    }

    @Override
    public boolean validateUserAccess(Long id) throws Exception {
        UserEntity currentUser = userService.getCurrentUser();
        long userIdFromProductEntity = findProductEntityById(id).getUserEntity().getId();
        return currentUser.getId() == userIdFromProductEntity || currentUser.getUsername().equals("admin@gmail.com");
    }

    @Override
    public void editProduct(ProductServiceModel productServiceModel, Long id, String notUpdateMyPicture) throws Exception {
        String imageUrl = this.findById(id).getImageUrl();

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

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void buyProduct(Long id) throws Exception {
        ProductEntity productEntityById = this.findProductEntityById(id);

        if (productEntityById.getId() > 0) {
            //Step 1: Map and save the bought product in purchasedProductEntity
            purchasedProductService.createPurchasedProduct(productEntityById);

            //Step 2: Delete the sold Product from Products Entities
            productRepository.deleteById(id);
        }
    }

    @Override
    public List<ProductViewModel> getAllProductsForCurrUser() throws Exception {
        return productRepository.
                findAllByUserEntity_Id(userService.getCurrentUser().getId()).
                stream().
                map(productEntity -> {
                    return modelMapper.map(productEntity, ProductViewModel.class);
                }).
                collect(Collectors.toList());


    }

    @Override
    public List<ProductViewModel> getAllProductsForUser(Long id) throws Exception {

        List<ProductViewModel> allProductsFromUserViewModel = new ArrayList<>();

        if (productRepository.findAllByUserEntity_Id(id).size() > 0) {
            allProductsFromUserViewModel.addAll(
                    productRepository.
                            findAllByUserEntity_Id(id).
                            stream().
                            map(productEntity -> modelMapper.map(productEntity, ProductViewModel.class)).
                            collect(Collectors.toList()));
        }

        return allProductsFromUserViewModel;
    }

    @Override
    public boolean amITheCreatorOfThisProduct(Long id) throws Exception {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        boolean creator = false;

        if (productEntity.isPresent()) {
            if (userService.getCurrentUser().getId() == productEntity.get().getUserEntity().getId()) {
                creator = true;
            }
            if (userService.getCurrentUser().getUsername().equals("admin@gmail.com")) {
                creator = true;
            }
        }
        return creator;
    }

    @Override
    public ProductViewModel findArchivedProductById(Long id) {

        ArchivedProductEntity archivedProductEntity = purchasedProductService.findPurchasedProductById(id);
        ProductViewModel productViewModel = modelMapper.map(archivedProductEntity, ProductViewModel.class);
        productViewModel.setCategoryName(archivedProductEntity.getCategoryEntity().getCategoryName());

        return productViewModel;
    }

    @Override
    public void seedProducts() {
        try {
            ProductEntity[] productEntities =
                    gson.fromJson(Files.
                            readString(Path.of(productsFile.getURI())), ProductEntity[].class);

            Arrays.stream(productEntities).
                    forEach(productEntity -> {
                        productEntity.
                                setCategoryEntity(categoryService.findCategoryByCategoryName(productEntity.getCategoryEntity().getCategoryName())).
                                setUserEntity(userService.findUserEntityByUsername(productEntity.getUserEntity().getUsername()));
                        productRepository.save(productEntity);
                    });
        } catch (IOException e) {
            throw new IllegalStateException("Sorry! The products cannot be seed in DB!!!");
        }
    }
}
