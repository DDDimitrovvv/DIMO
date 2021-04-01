package bg.softuni.service.impl;

import bg.softuni.model.entities.ArchivedProductEntity;
import bg.softuni.model.entities.ProductEntity;
import bg.softuni.repository.ArchivedProductRepository;
import bg.softuni.service.PurchasedProductService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchasedProductServiceImpl implements PurchasedProductService {

    private final ModelMapper modelMapper;
    private final ArchivedProductRepository archivedProductRepository;
    private final UserService userService;

    public PurchasedProductServiceImpl(ModelMapper modelMapper,
                                       ArchivedProductRepository archivedProductRepository,
                                       UserService userService) {
        this.modelMapper = modelMapper;
        this.archivedProductRepository = archivedProductRepository;
        this.userService = userService;
    }


    @Override
    public void createPurchasedProduct(ProductEntity productEntity) throws Exception {
        ArchivedProductEntity archivedProductEntity = modelMapper.map(productEntity, ArchivedProductEntity.class);
        archivedProductEntity.setId(0);
        archivedProductEntity.setPurchasedUserId(userService.getCurrentUser().getId());
        archivedProductEntity.setPurchasedUsername(userService.getCurrentUser().getUsername());
        archivedProductEntity.setPurchasedDateAndTime(LocalDateTime.now());
        archivedProductRepository.save(archivedProductEntity);
    }

    @Override
    public List<ArchivedProductEntity> getAllPurchasedProductByUserId() throws Exception {
        List<ArchivedProductEntity> purchasedProductList = new ArrayList<>();
        boolean emptyList = archivedProductRepository.findAllByPurchasedUserId(userService.getCurrentUser().getId()).isEmpty();
        if(!emptyList){
            purchasedProductList.addAll(archivedProductRepository.findAllByPurchasedUserId(userService.getCurrentUser().getId()));
        }
        return purchasedProductList;
    }

    @Override
    public ArchivedProductEntity findPurchasedProductById(Long id) {
        return archivedProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Purchased product not found! :("));
    }

    @Override
    public List<ArchivedProductEntity> getAllSoldProductsByUserId() throws Exception {
        List<ArchivedProductEntity> soldProductList = new ArrayList<>();
        boolean emptyList = archivedProductRepository.findAllByUserEntity_Id(userService.getCurrentUser().getId()).isEmpty();
        if(!emptyList){
            soldProductList.addAll(archivedProductRepository.findAllByUserEntity_Id(userService.getCurrentUser().getId()));
        }
        return soldProductList;
    }

    @Override
    public List<ArchivedProductEntity> getAllArchivedProducts() {
        List<ArchivedProductEntity> allArchivedProductsList = new ArrayList<>();
        if(archivedProductRepository.count() > 0){
            allArchivedProductsList.addAll(archivedProductRepository.listAllProductsSortedByDate());
        }
        return allArchivedProductsList;
    }


}
