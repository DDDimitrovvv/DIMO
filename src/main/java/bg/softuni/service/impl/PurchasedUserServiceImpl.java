package bg.softuni.service.impl;

import bg.softuni.model.entities.PurchasedProductEntity;
import bg.softuni.model.entities.PurchasedUserEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.repository.PurchasedUserRepository;
import bg.softuni.service.PurchasedUserService;
import bg.softuni.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchasedUserServiceImpl implements PurchasedUserService {

    private final PurchasedUserRepository purchasedUserRepository;
    private final UserService userService;

    public PurchasedUserServiceImpl(PurchasedUserRepository purchasedUserRepository,
                                    UserService userService) {
        this.purchasedUserRepository = purchasedUserRepository;
        this.userService = userService;
    }

    @Override
    public void savePurchasedProduct(UserEntity userEntity, PurchasedProductEntity purchasedProductEntity) {
        PurchasedUserEntity purchasedUserEntity = new PurchasedUserEntity();
        purchasedUserEntity.setUserEntity(userEntity);
        purchasedUserEntity.getProductEntityList().add(purchasedProductEntity);
        purchasedUserRepository.save(purchasedUserEntity);

    }

//    @Override
//    public List<PurchasedProductEntity> getAllSoldProductsByThisUser() throws Exception {
//        List<PurchasedProductEntity> purchasedProductEntityListList = new ArrayList<>();
//        UserEntity currentUser = userService.getCurrentUser();
//
//        if(purchasedUserRepository.count() == 0) {
//            return purchasedProductEntityListList;
//        }
//
//        PurchasedUserEntity purchasedUser = purchasedUserRepository.findByUserEntity_Username(currentUser.getUsername());
//
//        if(purchasedUser.getUserEntity().getId() > 0){
//            for ( PurchasedProductEntity purchasedProductEntity : purchasedUser.getProductEntityList() ){
//                if(purchasedProductEntity.getId() == currentUser.getId()){
//                    purchasedProductEntityListList.add(purchasedProductEntity);
//                }
//            }
//        }
//        return purchasedProductEntityListList;
//    }

    @Override
    public List<PurchasedProductEntity> getAllPurchasedProductsByThisUser() throws Exception {
        List<PurchasedProductEntity> purchasedProductEntityListList = new ArrayList<>();
        UserEntity currentUser = userService.getCurrentUser();

        if(purchasedUserRepository.count() == 0) {
            return purchasedProductEntityListList;
        }

        PurchasedUserEntity purchasedUser = purchasedUserRepository.findByUserEntity_Username(currentUser.getUsername());

        if(purchasedUser.getUserEntity().getId() > 0){
            purchasedProductEntityListList.addAll(purchasedUser.getProductEntityList());
        }
        return purchasedProductEntityListList;
    }
}
