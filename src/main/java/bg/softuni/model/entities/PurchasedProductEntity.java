package bg.softuni.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchased_products")
public class PurchasedProductEntity extends BaseEntity {

    @ManyToOne
    private UserEntity userEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "purchased_products_users_list")
            private List<ProductEntity> productEntityList = new ArrayList<>();

    public PurchasedProductEntity() {
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public PurchasedProductEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public PurchasedProductEntity setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
        return this;
    }
}
