package bg.softuni.web;

import bg.softuni.model.entities.ContactEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.enums.ContactCategoryTypeEnum;
import bg.softuni.repository.ContactRepository;
import bg.softuni.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ContactControllerTest {

    private long testContactID;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        this.init();
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testViewContactReturnOKStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact/us")).
                andExpect(status().isOk()).
                andExpect(view().name("contact")).
                andExpect(model().attributeExists("contactBindingModel"));
    }

    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testPostContactReturnRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/contact/us").
                with(csrf())).
                andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testPostContactReturnRedirectStatusSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/contact/us").
                param("categoryTypeEnum", "WARNING").
                param("messageText", "bla bla tatatata bla bla nanana nanana").
                param("submittedDateTime" , "2018-07-14T17:45:55.948353600").
                with(csrf())).
                andExpect(status().is3xxRedirection());
    }



    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testViewContactMessageReturnOKStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact/message/{id}", testContactID)).
                andExpect(status().isOk()).
                andExpect(view().name("contact-message-view")).
                andExpect(model().attributeExists("messageView"));
    }

    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testViewContactMessageReturnRedirect302Status() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact/message/{id}", testContactID)).
                andExpect(status().is3xxRedirection());
    }


    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testDeleteContactShouldReturnRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact/delete/{id}", testContactID)).
                andExpect(status().is3xxRedirection());
        Assertions.assertEquals(2, contactRepository.count());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testDeleteContactShouldReturnRedirectStatusWithAdminAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact/delete/{id}", testContactID)).
                andExpect(status().is3xxRedirection());
        Assertions.assertEquals(0, contactRepository.count());
    }


    private void init() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test@abv.bg");
        userEntity.setFullname("Test Testov");
        userEntity.setPassword("123456");

        if (userRepository.findByUsername("test@abv.bg").isPresent()) {
            userEntity = userRepository.findByUsername("test@abv.bg").get();
        } else {
            userEntity = userRepository.save(userEntity);
        }

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.
                setSubmittedDateTime(LocalDateTime.now()).
                setCategoryTypeEnum(ContactCategoryTypeEnum.WARNING).
                setMessageText("Help me. Be careful with all 3.5mm jack of HK.").
                setUserEntity(userEntity);
        contactRepository.save(contactEntity);
        testContactID = contactEntity.getId();
    }

}
