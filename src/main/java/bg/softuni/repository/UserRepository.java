package bg.softuni.repository;

import bg.softuni.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long>  {

  Optional<UserEntity> findByUsername(String userName);

  @Query("SELECT u.username FROM UserEntity u " +
          "ORDER BY u.username ASC")
  List<String> findAllUsernames();
}
