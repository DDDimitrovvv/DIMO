package bg.softuni.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchased_user")
public class PurchasedUserEntity extends BaseEntity{

    @ManyToOne
    private UserEntity userEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "purchased_products_users_list")
    private List<PurchasedProductEntity> productEntityList = new ArrayList<>();

    public PurchasedUserEntity() {
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public PurchasedUserEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public List<PurchasedProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public PurchasedUserEntity setProductEntityList(List<PurchasedProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
        return this;
    }
}
