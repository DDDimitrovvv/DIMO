package bg.softuni.repository;

import bg.softuni.model.entities.PurchasedProductEntity;
import bg.softuni.model.entities.PurchasedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedUserRepository extends JpaRepository<bg.softuni.model.entities.PurchasedUserEntity, Long> {

    PurchasedUserEntity findByUserEntity_Username(String username);
}
