package bg.softuni.web;

import bg.softuni.model.entities.*;
import bg.softuni.repository.*;
import bg.softuni.service.CloudinaryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ProductControllerTest {

    private static final String PRODUCT_CONTROLLER_PREFIX = "/products";
    private long testProductId;

    @MockBean
    CloudinaryService mockCloudinaryService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArchivedProductRepository archivedProductRepository;
    @Autowired
    private LogRepository logRepository;

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
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void testBuyProductShouldReturnRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                PRODUCT_CONTROLLER_PREFIX + "/buy/{id}", testProductId
        )).
                andExpect(status().is3xxRedirection());
        Assertions.assertEquals(15, productRepository.count());
        Assertions.assertEquals(2, archivedProductRepository.count());
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void testDeleteProductShouldReturnRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                PRODUCT_CONTROLLER_PREFIX + "/delete/{id}", testProductId
        )).
                andExpect(status().is3xxRedirection());
        Assertions.assertEquals(16, productRepository.count());
    }


//    @Test
//    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
//    void testArchiveProductShouldReturnValidStatus() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get(
//                PRODUCT_CONTROLLER_PREFIX + "/archived/{id}", testProductId
//        )).
//                andExpect(status().isOk()).
//                andExpect(view().name("archived_product-details")).
//                andExpect(model().attributeExists("archivedProduct"));
//    }


    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void testEditProductShouldReturnValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                PRODUCT_CONTROLLER_PREFIX + "/edit/{id}", testProductId
        )).
                andExpect(status().isOk()).
                andExpect(view().name("product-edit")).
                andExpect(model().attributeExists("productViewModel")).
                andExpect(model().attributeExists("categoryListItems"));
    }


    @Test
    @WithMockUser(value = "user@gmail.com", roles = {"USER"})
    void testEditProductShouldReturnRedirectStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                PRODUCT_CONTROLLER_PREFIX + "/edit/{id}", testProductId
        )).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/home"));

    }


//    @Test
//    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
//    void testEditProductShouldReturnRedirectStatusPostConfig() throws Exception {
//
//        MockMultipartFile mockImgFile
//                = new MockMultipartFile(
//                "imageUrl",
//                "helloMe.png",
//                MediaType.TEXT_PLAIN_VALUE,
//                "Hello, My Amazing World!".getBytes()
//        );
//
//
//        mockMvc.perform(MockMvcRequestBuilders.patch(PRODUCT_CONTROLLER_PREFIX + "/edit/{id}", testProductId)
//                .param("imageUrl", String.valueOf(mockImgFile)).
//                        param("Brand", "HKR")
//                .param("model", "AVR270")
//                .param("color", "yellow")
//                .param("price", "100")
//                .param("price", "100")
//                .param("details", "Your AVR includes Dolby Pro Logic IIz decoding, which uses the AVR’s Assigned Amp...")
//                .param("categoryName", "Receivers")
//                .param("notUpdateMyPicture", "")
//                .with(csrf())).
//                andExpect(status().is3xxRedirection()).
//                andExpect(view().name("redirect:add"));
//
////        Assertions.assertEquals(1, productRepository.count());
//    }


    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void testAddProductShouldReturnValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                PRODUCT_CONTROLLER_PREFIX + "/add"
        )).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("productAddBindingModel")).
                andExpect(model().attributeExists("categoryList"));
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void testAddProductShouldReturnInvalidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                PRODUCT_CONTROLLER_PREFIX + "/add+"
        )).
                andExpect(status().is(404)).
                andExpect(status().is4xxClientError());
    }


    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void testShouldReturnValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                PRODUCT_CONTROLLER_PREFIX + "/details/{id}", testProductId
        )).
                andExpect(status().isOk()).
                andExpect(view().name("product-details")).
                andExpect(model().attributeExists("product")).
                andExpect(model().attributeExists("editAccess"));
    }


    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void addProduct() throws Exception {

        MockMultipartFile mockImgFile
                = new MockMultipartFile(
                "imageUrl",
                "hello.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(
                PRODUCT_CONTROLLER_PREFIX + "/add")
                .file(mockImgFile)
                .param("brand", "HK")
                .param("model", "AVR270")
                .param("color", "black")
                .param("manufactureDate", "2019-01-09")
                .param("price", "100")
                .param("warranty", "12")
                .param("details", "Your AVR includes Dolby Pro Logic IIz decoding, which uses the AVR’s Assigned Amp...")
                .param("categoryName", "Receivers")
                .with(csrf())).
                andExpect(status().is3xxRedirection());

        Assertions.assertEquals(17, productRepository.count());
    }


    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER"})
    void addProductInvalidInput() throws Exception {

        MockMultipartFile mockImgFile
                = new MockMultipartFile(
                "imageUrl",
                "hello.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(
                PRODUCT_CONTROLLER_PREFIX + "/add")
                .file(mockImgFile)
                .param("Brand", "HK")
                .param("model", "AVR270")
                .param("coloT", "black")
                .param("manufactureDate", "2019-01-09")
                .param("price", "100")
                .param("warranty", "12")
                .param("details", "Your AVR includes Dolby Pro Logic IIz decoding, which uses the AVR’s Assigned Amp...")
                .param("categoryName", "Receivers")
                .with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:add"));

        Assertions.assertEquals(16, productRepository.count());
    }

    private void init() {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName("Speakers");
        categoryEntity.setDescription("Cars came into global use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");
        categoryEntity = categoryRepository.save(categoryEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test@abv.bg");
        userEntity.setFullname("Test Testov");
        userEntity.setPassword("123456");

        if (userRepository.findByUsername("test@abv.bg").isPresent()) {
            userEntity = userRepository.findByUsername("test@abv.bg").get();
        } else {
            userEntity = userRepository.save(userEntity);
        }


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
        testProductId = productEntity.getId();

        ArchivedProductEntity archivedProductEntity = modelMapper.map(productEntity, ArchivedProductEntity.class);
        archivedProductEntity.setId(0);
        archivedProductEntity.setPurchasedUserId(userEntity.getId());
        archivedProductEntity.setPurchasedUsername(userEntity.getUsername());
        archivedProductEntity.setPurchasedDateAndTime(LocalDateTime.now());
        archivedProductRepository.save(archivedProductEntity);
    }
}