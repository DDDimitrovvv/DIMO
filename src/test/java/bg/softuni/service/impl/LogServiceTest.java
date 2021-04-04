package bg.softuni.service.impl;


import bg.softuni.model.entities.CategoryEntity;
import bg.softuni.model.entities.LogEntity;
import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.view.LogViewModel;
import bg.softuni.repository.LogRepository;
import bg.softuni.service.ProductService;
import bg.softuni.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LogServiceTest {

    private LogServiceImpl logServiceTest;

    private LogEntity logEntity1, logEntity2;
    private UserEntity userEntity1, userEntity2;
    private CategoryEntity categoryEntity1, categoryEntity2;
    private ProductEntity productEntity1, productEntity2;

    @Mock
    LogRepository mockLogRepository;

    @Mock
    UserService mockUserService;

    @Mock
    ProductService mockProductService;

    @Autowired
    ModelMapper mockModelMapper;

    @BeforeEach
    public void setUp() {
        this.init();
        logServiceTest = new LogServiceImpl(mockLogRepository, mockProductService, mockUserService, mockModelMapper);

    }

    @Test
    public void getAllLogsOfUsersTestValid() {
        when(mockLogRepository.findAll()).thenReturn(List.of(logEntity1, logEntity2));

        List<LogViewModel> allLogs = logServiceTest.getAllLogsOfUsers();

        Assertions.assertEquals(2, allLogs.size());

        LogViewModel log1 = allLogs.get(0);
        LogViewModel log2 = allLogs.get(1);

        Assertions.assertEquals(logEntity1.getProductEntity().getBrand(), log1.getProductBrand());
        Assertions.assertEquals(logEntity1.getUserEntity().getUsername(), log1.getUsername());
        Assertions.assertEquals(logEntity1.getActionPage(), log1.getActionPage());
        Assertions.assertEquals(logEntity1.getLocalDateTime(), log1.getLocalDateTime());

        Assertions.assertEquals(logEntity2.getProductEntity().getBrand(), log2.getProductBrand());
        Assertions.assertEquals(logEntity2.getUserEntity().getUsername(), log2.getUsername());
        Assertions.assertEquals(logEntity2.getActionPage(), log2.getActionPage());
        Assertions.assertEquals(logEntity2.getLocalDateTime(), log2.getLocalDateTime());
    }

    @Test
    public void  deleteAllLogs(){
        logServiceTest.deleteAllLogsEntities();
        Mockito.verify(mockLogRepository).deleteAll();
        Assertions.assertEquals(0,mockLogRepository.count());
    }

    private void init() {

        categoryEntity1 = new CategoryEntity();
        categoryEntity1.setCategoryName("Speakers_NEW");
        categoryEntity1.setDescription("Cars came into global use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");

        categoryEntity2 = new CategoryEntity();
        categoryEntity2.setCategoryName("Speakers_OLD");
        categoryEntity2.setDescription("Cars use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");

        userEntity1 = new UserEntity();
        userEntity1.setUsername("test1@abv.bg");
        userEntity1.setFullname("Test Testov");
        userEntity1.setPassword("123456");

        userEntity2 = new UserEntity();
        userEntity2.setUsername("test2@abv.bg");
        userEntity2.setFullname("Test Ivanovich");
        userEntity2.setPassword("123456");

        productEntity1 = new ProductEntity();
        productEntity1.setBrand("JBL");
        productEntity1.setModel("E80");
        productEntity1.setColor("black");
        productEntity1.setImageUrl("https://res.cloudinary.com/dsrmaoof8/image/upload/v1616877657/e2dkd8tro6bxkgfg51kn.png");
        productEntity1.setDetails("Cars came into global use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");
        productEntity1.setPrice(BigDecimal.TEN);
        productEntity1.setManufactureDate(LocalDate.of(2018, 4, 5));
        productEntity1.setWarranty(12);
        productEntity1.setUserEntity(userEntity1);
        productEntity1.setCategoryEntity(categoryEntity1);

        productEntity2 = new ProductEntity();
        productEntity2.setBrand("JBLs");
        productEntity2.setModel("E80 NL");
        productEntity2.setColor("black");
        productEntity2.setImageUrl("https://res.clRudinary.com/dsrmaoof8/image/upload/v1616877657/e2dkd8tro6bxkgfg51kn.png");
        productEntity2.setDetails("Cars came into global use during the 20th century, and developed economies depend on them. The year 1886 is regarded as the birth year of the modern car when German inventor Karl Benz patented his Benz Patent-Motorwagen. Cars became widely available in the early 20th century.");
        productEntity2.setPrice(BigDecimal.TEN);
        productEntity2.setManufactureDate(LocalDate.of(2018, 4, 5));
        productEntity2.setWarranty(12);
        productEntity2.setUserEntity(userEntity2);
        productEntity2.setCategoryEntity(categoryEntity2);

        logEntity1 = new LogEntity();
        logEntity1.
                setProductEntity(productEntity1).
                setLocalDateTime(LocalDateTime.now()).
                setActionPage("details").
                setUserEntity(userEntity1);

        logEntity2 = new LogEntity();
        logEntity2.
                setProductEntity(productEntity2).
                setLocalDateTime(LocalDateTime.now()).
                setActionPage("details").
                setUserEntity(userEntity2);
    }
}
