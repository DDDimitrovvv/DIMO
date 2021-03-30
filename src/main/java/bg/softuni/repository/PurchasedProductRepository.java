package bg.softuni.repository;

import bg.softuni.model.entities.PurchasedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedProductRepository extends JpaRepository<PurchasedProductEntity, Long> {

}
