package bg.softuni.service;

import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.service.ProductServiceModel;
import bg.softuni.model.view.ProductViewModel;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductViewModel> getListWithAllProducts();

    void addProduct(ProductServiceModel productServiceModel) throws Exception;

    Map<String, Integer> availableProductInDB();

    ProductViewModel findById(Long id);

    ProductEntity findProductEntityById(Long productId);

    boolean validateUserAccess(Long id) throws Exception;

    void editProduct(ProductServiceModel productServiceModel, Long id, String notUpdateMyPicture) throws Exception;

    void deleteProduct(Long id);

    void buyProduct(Long id) throws Exception;

    List<ProductViewModel> getAllProductsForCurrUser() throws Exception;

    List<ProductViewModel> getAllProductsForUser(Long id) throws Exception;

    boolean amITheCreatorOfThisProduct(Long id) throws Exception;

    ProductViewModel findArchivedProductById(Long id);

    void seedProducts();
}
