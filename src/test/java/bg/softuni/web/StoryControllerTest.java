package bg.softuni.web;

import bg.softuni.model.entities.StoryEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.enums.StoryTypeEnum;
import bg.softuni.repository.StoryRepository;
import bg.softuni.repository.UserRepository;
import bg.softuni.service.CloudinaryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class StoryControllerTest {
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
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testViewAllStoriesReturnValidStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stories/all")).
                andExpect(status().isOk()).
                andExpect(view().name("stories")).
                andExpect(model().attributeExists("storiesCollection"));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testAddViewStoryReturnValidStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stories/add")).
                andExpect(status().isOk()).
                andExpect(view().name("story-add"));
    }


    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testStoryDetailsReturnValidStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stories/details/{id}", testStoryId)).
                andExpect(status().isOk()).
                andExpect(view().name("story-details")).
                andExpect(model().attributeExists("story")).
                andExpect(model().attributeExists("editAccess"));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testStoryDetailsReturnValidNOTStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stories/details/{id}", testStoryId)).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testStoryDeleteReturnValidStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stories/delete/{id}", testStoryId)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/stories/all"));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testStoryDeleteReturnValidStatusRedirection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stories/delete/{id}", testStoryId)).
                andExpect(status().is3xxRedirection());
    }


    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testEditStoryPageStatusRedirectPatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/stories/edit/{id}", testStoryId).
                with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/stories/edit/{id}"));
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg", authorities = {"USER"})
    void testEditUserWithExistsEmailWithProfilePageStatusRedirectPatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/stories/edit/{id}", testStoryId).
                param("title","WOW What a power!!!!").
                param("description","AVR includes Dolby Pro Logic IIz decoding, which uses the AVR’s Ass").
                param("storyTypeEnum", "INFO").
                param("notUpdateMyPicture", "true").
                param("imageUrl", "").
                with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/stories/edit/{id}"));
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void addStoryValidInput() throws Exception {

        MockMultipartFile mockImgFile
                = new MockMultipartFile(
                "imageUrl",
                "hello.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(
                "/stories/add")
                .file(mockImgFile)
                .param("title", "Wooow bass shake")
                .param("description", "Your AVR includes Dolby Pro Logic IIz decoding, which uses the AVR’s Assigned")
                .param("storyTypeEnum", "INFO")
                .with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/stories/all"));

        Assertions.assertEquals(7, storyRepository.count());
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void addStoryInvalidInput() throws Exception {

        MockMultipartFile mockImgFile
                = new MockMultipartFile(
                "imageUrl",
                "hello.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(
                "/stories/add")
                .file(mockImgFile)
                .param("tiRle", "Wooow")
                .param("description", "Your AVR includes Dolby Pro Logic IIz decoding, which uses the AVR’s Assigned")
                .param("storyTypeEnum", "INFO")
                .with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:add"));

        Assertions.assertEquals(6, storyRepository.count());
    }


//    @PostMapping("/add")
//    public String addProduct(@Valid StoryAddBindingModel storyAddBindingModel,
//                             BindingResult bindingResult,
//                             RedirectAttributes redirectAttributes) throws Exception {
//
//        if (bindingResult.hasErrors() || storyAddBindingModel.getImageUrl().isEmpty()) {
//            redirectAttributes.addFlashAttribute("storyAddBindingModel", storyAddBindingModel);
//            redirectAttributes.addFlashAttribute(
//                    "org.springframework.validation.BindingResult.storyAddBindingModel",
//                    bindingResult);
//            return "redirect:add";
//        }
//
//        //save story in DB
//        storyService
//                .addStory(modelMapper.map(storyAddBindingModel, StoryServiceModel.class));
//
//        return "redirect:/stories/all";
//    }

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
