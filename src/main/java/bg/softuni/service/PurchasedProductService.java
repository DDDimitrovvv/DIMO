package bg.softuni.service;

import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.PurchasedProductEntity;

import java.util.List;

public interface PurchasedProductService {
    void createPurchasedProduct(ProductEntity productEntity) throws Exception;

    List<PurchasedProductEntity> getAllProductSoldByUserId() throws Exception;
}
