package bg.softuni.service.impl;

import bg.softuni.model.entities.ContactEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.enums.ContactCategoryTypeEnum;
import bg.softuni.model.service.ContactServiceModel;
import bg.softuni.repository.ContactRepository;
import bg.softuni.repository.UserRepository;
import bg.softuni.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    private UserEntity userEntity;
    private ContactServiceImpl contactServiceToTest;

    @Autowired
    UserService mockUserService;
    @Autowired
    ContactRepository mockContactRepository;
    @Autowired
    UserRepository mockUserRepository;
    @Autowired
    ModelMapper mockModelMapper;

    @BeforeEach
    public void setUp() {
        this.init();
        contactServiceToTest = new ContactServiceImpl(mockUserService, mockContactRepository, mockModelMapper);
        mockUserService = Mockito.mock(UserService.class);
        mockContactRepository = Mockito.mock(ContactRepository.class);
        mockModelMapper = new ModelMapper();
    }

    @Test
    @WithMockUser(username = "test@abv.bg", authorities = {"USER"})
    public void testAddMessage() throws Exception {

        ContactEntity contact = new ContactEntity();
        contact.
                setUserEntity(mockUserService.getCurrentUser()).
                setMessageText("Bla bla bla! Dracula said that yesterday! He is very scary person. He likes the blood.").
                setCategoryTypeEnum(ContactCategoryTypeEnum.INFO).
                setSubmittedDateTime(LocalDateTime.now());

        mockContactRepository.save(contact);
        ContactServiceModel contactServiceModel = mockModelMapper.map(contact, ContactServiceModel.class);
        contactServiceToTest.addContactMessage(contactServiceModel);

        Mockito.verify(mockContactRepository).save(contact);
    }

    private void init() {

        if (mockUserRepository.findByUsername("test@abv.bg").isPresent()) {
            userEntity = mockUserRepository.findByUsername("test@abv.bg").get();
        } else {
            userEntity = new UserEntity();
            userEntity.setUsername("test@abv.bg");
            userEntity.setFullname("Test Testov");
            userEntity.setPassword("123456");
            userEntity = mockUserRepository.save(userEntity);
        }
    }

}
