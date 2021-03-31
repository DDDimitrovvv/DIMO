package bg.softuni.service;

import bg.softuni.model.entities.PurchasedProductEntity;
import bg.softuni.model.entities.PurchasedUserEntity;
import bg.softuni.model.entities.UserEntity;

import java.util.List;

public interface PurchasedUserService {
    void savePurchasedProduct(UserEntity userEntity, PurchasedProductEntity purchasedProductEntity);

//    List<PurchasedProductEntity> getAllSoldProductsByThisUser() throws Exception;

    List<PurchasedProductEntity> getAllPurchasedProductsByThisUser() throws Exception;
}
