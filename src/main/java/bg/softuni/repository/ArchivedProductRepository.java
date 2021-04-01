package bg.softuni.repository;

import bg.softuni.model.entities.ArchivedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivedProductRepository extends JpaRepository<ArchivedProductEntity, Long> {

    List<ArchivedProductEntity> findAllByPurchasedUserId(Long id);

    List<ArchivedProductEntity> findAllByUserEntity_Id(Long id);

    @Query(value = "SELECT p FROM ArchivedProductEntity AS p ORDER BY p.purchasedDateAndTime DESC")
    List<ArchivedProductEntity> listAllProductsSortedByDate();
}
