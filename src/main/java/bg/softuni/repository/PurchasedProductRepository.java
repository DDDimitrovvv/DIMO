package bg.softuni.repository;

import bg.softuni.model.entities.PurchasedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedProductRepository extends JpaRepository<PurchasedProductEntity, Long> {

    List<PurchasedProductEntity> findAllByUserEntity_Id(Long id);
}
