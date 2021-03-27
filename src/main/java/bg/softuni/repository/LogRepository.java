package bg.softuni.repository;

import bg.softuni.model.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {

//    @Query(value = "SELECT logs from LogEntity AS logs WHERE ProductEntity.id = ?1")
//    List<LogEntity> findLogByProductId(Long id);
    List<LogEntity> findAllByProductEntity_Id(Long id);
}
