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

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws IOException {
        this.init();
    }


    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testLoginPageReturnValidStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login")).
                andExpect(status().isOk()).
                andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testRegisterPageReturnValidStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register")).
                andExpect(status().isOk()).
                andExpect(view().name("register"));
    }


    @Test
    void testLoginPageReturnRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login-error").
                param("username", "te@abv.bg").
                param("password", "123456")
                .with(csrf())).
                andExpect(status().is3xxRedirection());
    }

    @Test
    void testRegisterPageReturnInvalidRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register").
                param("username", "borisabv.bg").
                param("fullname", "Boris Ivanov").
                param("password", "3456789").
                param("confirmPassword", "13456789")
                .with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/users/register"));
    }

    @Test
    void testRegisterPageReturnInvalidRedirectStatusForAlreadyCreatedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register").
                param("username", "test@abv.bg").
                param("fullname", "Boris Ivanov").
                param("password", "123456").
                param("confirmPassword", "123456")
                .with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/users/register"));
    }

    @Test
    void testRegisterPageReturnValidRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register").
                param("username", "boris@abv.bg").
                param("fullname", "Boris Ivanov").
                param("password", "13456789").
                param("confirmPassword", "13456789")
                .with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/home"));
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
