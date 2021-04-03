package bg.softuni.web;

import bg.softuni.model.entities.UserEntity;
import bg.softuni.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ProfileControllerTest {

    private long testUserId;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        this.init();
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg", authorities = {"USER"})
    void testEditUserWithMasterUserPageStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/edit/{id}", testUserId)).
                andExpect(status().isOk()).
                andExpect(view().name("profile-edit")).
                andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testEditUserPageWithNormalUserStatusRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/edit/{id}", testUserId)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testEditUserPageStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/edit/{id}", testUserId)).
                andExpect(status().isOk()).
                andExpect(view().name("profile-edit")).
                andExpect(model().attributeExists("user"));
    }


    @Test
    @WithMockUser(username = "ivan@abv.bg", authorities = {"USER"})
    void testViewUserWithMasterUserPageStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/view")).
                andExpect(status().isOk()).
                andExpect(view().name("profile")).
                andExpect(model().attributeExists("user")).
                andExpect(model().attributeExists("isRootAdmin")).
                andExpect(model().attributeExists("isAdmin")).
                andExpect(model().attributeExists("userProductsList")).
                andExpect(model().attributeExists("userStoriesList")).
                andExpect(model().attributeExists("userPurchasedList")).
                andExpect(model().attributeExists("userSoldList")).
                andExpect(model().attributeExists("showAllArchivedProducts")).
                andExpect(model().attributeExists("showMessages"));
    }


    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testViewUserWithAdminProfilePageStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/view")).
                andExpect(status().isOk()).
                andExpect(view().name("profile")).
                andExpect(model().attributeExists("user")).
                andExpect(model().attributeExists("isRootAdmin")).
                andExpect(model().attributeExists("isAdmin")).
                andExpect(model().attributeExists("userProductsList")).
                andExpect(model().attributeExists("userStoriesList")).
                andExpect(model().attributeExists("userPurchasedList")).
                andExpect(model().attributeExists("userSoldList")).
                andExpect(model().attributeExists("showAllArchivedProducts")).
                andExpect(model().attributeExists("showMessages"));
    }


    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testEditUserWithAdminProfilePageStatusRedirectPatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/profile/edit/{id}", testUserId).
                with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/profile/edit/{id}"));
    }


    @Test
    @WithMockUser(username = "ivan@abv.bg", authorities = {"USER", "ADMIN"})
    void testEditUserWithExistsEmailWithProfilePageStatusRedirectPatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/profile/edit/{id}", testUserId).
                param("username","admin@gmail.com").
                param("password","123456").
                with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/profile/edit/{id}"));
    }


    private void init() {

        UserEntity userEntity = new UserEntity();
        if (userRepository.findByUsername("ivan@abv.bg").isPresent()) {
            userEntity = userRepository.findByUsername("ivan@abv.bg").get();
        } else {
            userEntity.setUsername("ivan@abv.bg");
            userEntity.setFullname("Ivan Ivanov");
            userEntity.setPassword("123456");
            userEntity = userRepository.save(userEntity);
        }
        testUserId = userEntity.getId();
    }

}
