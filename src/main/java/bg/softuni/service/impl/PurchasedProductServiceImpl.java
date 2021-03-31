package bg.softuni.service.impl;

import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.PurchasedProductEntity;
import bg.softuni.repository.PurchasedProductRepository;
import bg.softuni.service.PurchasedProductService;
import bg.softuni.service.PurchasedUserService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchasedProductServiceImpl implements PurchasedProductService {

    private final ModelMapper modelMapper;
    private final PurchasedProductRepository purchasedProductRepository;
    private final PurchasedUserService purchasedUserService;
    private final UserService userService;

    public PurchasedProductServiceImpl(ModelMapper modelMapper,
                                       PurchasedProductRepository purchasedProductRepository,
                                       PurchasedUserService purchasedUserService,
                                       UserService userService) {
        this.modelMapper = modelMapper;
        this.purchasedProductRepository = purchasedProductRepository;
        this.purchasedUserService = purchasedUserService;
        this.userService = userService;
    }


    @Override
    public void createPurchasedProduct(ProductEntity productEntity) throws Exception {
        PurchasedProductEntity purchasedProductEntity = modelMapper.map(productEntity, PurchasedProductEntity.class);
        purchasedProductEntity.setId(0);
        purchasedProductRepository.save(purchasedProductEntity);
        purchasedUserService.savePurchasedProduct(userService.getCurrentUser(), purchasedProductEntity);
    }

    @Override
    public List<PurchasedProductEntity> getAllProductSoldByUserId() throws Exception {
        return purchasedProductRepository.findAllByUserEntity_Id(userService.getCurrentUser().getId());
    }


}
