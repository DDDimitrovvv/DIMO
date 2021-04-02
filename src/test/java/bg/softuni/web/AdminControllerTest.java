package bg.softuni.web;

import bg.softuni.model.entities.*;
import bg.softuni.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws IOException {
        this.init();
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testViewAdminStatsReturnValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/statistics")).
                andExpect(status().isOk()).
                andExpect(view().name("statistics")).
                andExpect(model().attributeExists("loggedUsersCount")).
                andExpect(model().attributeExists("registeredUsersCount")).
                andExpect(model().attributeExists("totalProductCount")).
                andExpect(model().attributeExists("totalSpeakerProductCount")).
                andExpect(model().attributeExists("totalReceiverProductCount")).
                andExpect(model().attributeExists("totalAccessoriesProductCount")).
                andExpect(model().attributeExists("totalHome_CinemaProductCount")).
                andExpect(model().attributeExists("totalStoriesCount")).
                andExpect(model().attributeExists("totalFunStoriesCount")).
                andExpect(model().attributeExists("totalIssueStoriesCount")).
                andExpect(model().attributeExists("totalInfoStoriesCount")).
                andExpect(model().attributeExists("localDate")).
                andExpect(model().attributeExists("isAvailableUserChoice")).
                andExpect(model().attributeExists("userChoice"));

    }

    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testViewAdminStatsReturnRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/statistics")).
                andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testAddRoleReturnRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/roles")).
                andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testAddRoleReturnValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/roles")).
                andExpect(status().isOk()).
                andExpect(view().name("roles")).
                andExpect(model().attributeExists("usernameList"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testEditRoleReturnValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/roles").
                param("username", "user@gmail.com").
                param("role", "ADMIN").
                with(csrf())).
                andExpect(status().is3xxRedirection());
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

    }
}
