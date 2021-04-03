package bg.softuni.web;

import bg.softuni.model.entities.StoryEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.enums.StoryTypeEnum;
import bg.softuni.repository.StoryRepository;
import bg.softuni.repository.UserRepository;
import bg.softuni.service.CloudinaryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class StoryRestControllerTest {

    private long testStoryId;

    @MockBean
    CloudinaryService mockCloudinaryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryRepository storyRepository;

    @BeforeEach
    public void setUp() throws IOException {
        when(mockCloudinaryService.uploadImage(Mockito.any())).thenReturn("https://pixar.fandom.com/wiki/Cars?file=Haa.jpg");
        this.init();
    }

    @AfterEach
    public void setDown() {
        if (storyRepository.findById(testStoryId).isPresent()) {
            storyRepository.deleteById(testStoryId);
        }
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg", roles = {"USER"})
    public void testShowStoriesRestReturnCorrectStatusCodeAccess() throws Exception {
        this.mockMvc.perform(get("/stories/api")).andExpect(status().isOk());
    }


    private void init() {
        UserEntity userEntity = new UserEntity();

        if (userRepository.findByUsername("test@abv.bg").isPresent()) {
            userEntity = userRepository.findByUsername("test@abv.bg").get();
        } else {
            userEntity.setUsername("test@abv.bg");
            userEntity.setFullname("Test Testov");
            userEntity.setPassword("123456");
            userEntity = userRepository.save(userEntity);
        }

        StoryEntity storyEntity = new StoryEntity();
        storyEntity.
                setUserEntity(userEntity).
                setAddedDate(LocalDate.now()).
                setImageUrl("https://res.cloudinary.com/dsrmaoof8/image/upload/v1616877657/e2dkd8tro6bxkgfg51kn.png").
                setDescription("Trala la la Tralalalala Tararam tipi tam. My name is Capitan!").
                setTitle("Very massive bass...").
                setProductLink("https://en.wikipedia.org/wiki/Bass").
                setStoryTypeEnum(StoryTypeEnum.INFO);
        storyRepository.save(storyEntity);
        testStoryId = storyEntity.getId();
    }
}
