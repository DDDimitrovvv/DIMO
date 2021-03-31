package bg.softuni.repository;

import bg.softuni.model.entities.StoryEntity;
import bg.softuni.model.entities.enums.StoryTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {

    List<StoryEntity> findAllByStoryTypeEnum(StoryTypeEnum storyType);

    List<StoryEntity> findAllByUserEntity_Id(Long id);
}
