package bg.softuni.service.impl;

import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.UserRoleEntity;
import bg.softuni.model.entities.enums.UserRole;
import bg.softuni.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class DimoDBUserServiceTest {

    private DimoDBUserService serviceToTest;


    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new DimoDBUserService(mockUserRepository);
    }

    @Test
    public void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class, () -> {
                    serviceToTest.loadUserByUsername("user_does_not_exist");
                }
        );
    }

    @Test
    public void testExistingUser() {
        //prepare data
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test@abv.bg");
        userEntity.setFullname("Test Testov");
        userEntity.setPassword("123456");
        UserRoleEntity roleUser = new UserRoleEntity();
        roleUser.setRole(UserRole.USER);
        UserRoleEntity roleAdmin = new UserRoleEntity();
        roleAdmin.setRole(UserRole.ADMIN);
        userEntity.setRoles(List.of(roleAdmin, roleUser));

        //configure mocks
        Mockito.when(mockUserRepository.findByUsername("test@abv.bg")).
                thenReturn(Optional.of(userEntity));

        //test
        UserDetails userDetails = serviceToTest.loadUserByUsername("test@abv.bg");

        Assertions.assertEquals(userEntity.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        List<String> authorities = userDetails.getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());

        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
    }


}
