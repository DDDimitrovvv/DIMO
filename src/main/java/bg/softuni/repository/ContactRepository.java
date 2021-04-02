package bg.softuni.repository;

import bg.softuni.model.entities.ArchivedProductEntity;
import bg.softuni.model.entities.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    @Query(value = "SELECT c FROM ContactEntity AS c ORDER BY c.submittedDateTime DESC")
    List<ContactEntity> listAllContactsMessagesSortedByDate();
}
