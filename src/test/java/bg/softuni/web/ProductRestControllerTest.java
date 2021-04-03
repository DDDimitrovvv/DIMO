package bg.softuni.web;

import bg.softuni.model.entities.*;
import bg.softuni.repository.CategoryRepository;
import bg.softuni.repository.LogRepository;
import bg.softuni.repository.ProductRepository;
import bg.softuni.repository.UserRepository;
import bg.softuni.service.CloudinaryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ProductRestControllerTest {

    private long testProductId;

    @MockBean
    CloudinaryService mockCloudinaryService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() throws IOException {
        when(mockCloudinaryService.uploadImage(Mockito.any())).thenReturn("https://pixar.fandom.com/wiki/Cars?file=Haa.jpg");
        this.init();
    }

    @AfterEach
    public void setDown() {
        if (productRepository.findById(testProductId).isPresent()) {
            List<LogEntity> logEntities = logRepository.findAllByProductEntity_Id(testProductId);
            logEntities.forEach(logRepository::delete);
            productRepository.deleteById(testProductId);
        }
    }


    @Test
    @WithMockUser(username = "ivan@abv.bg", roles = {"USER"})
    public void testShopRestReturnCorrectStatusCodeAccess() throws Exception {
        this.mockMvc.perform(get("/products/api")).andExpect(status().isOk());
    }

    private void init() {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName("Receivers");
        categoryEntity.setDescription("Cars came into global use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");
        categoryEntity = categoryRepository.save(categoryEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("ivan@abv.bg");
        userEntity.setFullname("Ivan Ivanov");
        userEntity.setPassword("123456");

        if (userRepository.findByUsername("ivan@abv.bg").isPresent()) {
            userEntity = userRepository.findByUsername("ivan@abv.bg").get();
        } else {
            userEntity = userRepository.save(userEntity);
        }


        ProductEntity productEntity = new ProductEntity();
        productEntity.setBrand("HK");
        productEntity.setModel("AVR270");
        productEntity.setColor("black");
        productEntity.setImageUrl("https://res.cloudinary.com/dsrmaoof8/image/upload/v1616877657/e2dkd8tro6bxkgfg51kn.png");
        productEntity.setDetails("HK so poewrfull... came into global use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");
        productEntity.setPrice(BigDecimal.TEN);
        productEntity.setManufactureDate(LocalDate.of(2020, 4, 5));
        productEntity.setWarranty(36);
        productEntity.setUserEntity(userEntity);
        productEntity.setCategoryEntity(categoryEntity);
        productEntity = productRepository.save(productEntity);
        testProductId = productEntity.getId();
    }
}
