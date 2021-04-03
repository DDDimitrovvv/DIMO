package bg.softuni.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class HomeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testHomePageStatusRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/home"));
    }


    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testHomePageStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home")).
                andExpect(status().isOk()).
                andExpect(view().name("home")).
                andExpect(model().attributeExists("currentUserFullName")).
                andExpect(model().attributeExists("allProducts")).
                andExpect(model().attributeExists("categoryList"));

    }

    @Test
    void testHomePageWithoutAuthUserStatusRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home")).
                andExpect(status().is3xxRedirection());
    }

    @Test
    void testHomePageDashWithoutAuthUserStatusRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).
                andExpect(status().isOk()).
                andExpect(view().name("index"));
    }

    @Test
    void testAboutPageWithoutAuthUserStatusRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about")).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"USER"})
    void testAboutPageWithAuthUserStatusRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about")).
                andExpect(status().isOk());
    }
}
