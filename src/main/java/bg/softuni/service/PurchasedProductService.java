package bg.softuni.service;

import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.ArchivedProductEntity;

import java.util.List;

public interface PurchasedProductService {
    void createPurchasedProduct(ProductEntity productEntity) throws Exception;

    List<ArchivedProductEntity> getAllPurchasedProductByUserId() throws Exception;

    ArchivedProductEntity findPurchasedProductById(Long id);

    List<ArchivedProductEntity> getAllSoldProductsByUserId() throws Exception;

    List<ArchivedProductEntity>  getAllArchivedProducts();

    void deletePurchasedProductByUserEntityId(Long id);
}
