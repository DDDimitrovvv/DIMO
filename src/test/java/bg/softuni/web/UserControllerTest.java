package bg.softuni.web;


import bg.softuni.model.entities.ArchivedProductEntity;
import bg.softuni.model.entities.CategoryEntity;
import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.repository.ArchivedProductRepository;
import bg.softuni.repository.CategoryRepository;
import bg.softuni.repository.ProductRepository;
import bg.softuni.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {

    private long testUserID;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ArchivedProductRepository archivedProductRepository;

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
                param("password", "123456").
                with(csrf())).
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




    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testDeleteUserShouldReturnRedirectStatusCorrectUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/delete/{id}", testUserID)).
                andExpect(status().is3xxRedirection());
        Assertions.assertEquals(8, userRepository.count());
    }

    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    void testDeleteContactShouldReturnRedirectStatusIncorrectUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/delete/{id}", 15L)).
                andExpect(status().is3xxRedirection());
        Assertions.assertEquals(8, userRepository.count());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = {"USER", "ADMIN"})
    void testDeleteContactShouldReturnRedirectStatusWithAdminAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/delete/{id}", testUserID)).
                andExpect(status().is3xxRedirection());
        Assertions.assertEquals(7, userRepository.count());
    }


    private void init() {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName("Speakers");
        categoryEntity.setDescription("Cars came into global use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");
        categoryEntity = categoryRepository.save(categoryEntity);

        UserEntity userEntity = new UserEntity();

        if (userRepository.findByUsername("test@abv.bg").isPresent()) {
            userEntity = userRepository.findByUsername("test@abv.bg").get();
        } else {
            userEntity.setUsername("test@abv.bg");
            userEntity.setFullname("Test Testov");
            userEntity.setPassword("123456");
            userEntity = userRepository.save(userEntity);
        }
        testUserID = userEntity.getId();

        ProductEntity productEntity = new ProductEntity();
        productEntity.setBrand("JBL");
        productEntity.setModel("E80");
        productEntity.setColor("black");
        productEntity.setImageUrl("https://res.cloudinary.com/dsrmaoof8/image/upload/v1616877657/e2dkd8tro6bxkgfg51kn.png");
        productEntity.setDetails("Cars came into global use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");
        productEntity.setPrice(BigDecimal.TEN);
        productEntity.setManufactureDate(LocalDate.of(2018, 4, 5));
        productEntity.setWarranty(12);
        productEntity.setUserEntity(userEntity);
        productEntity.setCategoryEntity(categoryEntity);
        productEntity = productRepository.save(productEntity);

        ArchivedProductEntity archivedProductEntity = modelMapper.map(productEntity, ArchivedProductEntity.class);
        archivedProductEntity.setId(0);
        archivedProductEntity.setPurchasedUserId(userEntity.getId());
        archivedProductEntity.setPurchasedUsername(userEntity.getUsername());
        archivedProductEntity.setPurchasedDateAndTime(LocalDateTime.now());
        archivedProductRepository.save(archivedProductEntity);


    }

}
