package bg.softuni.service.impl;

import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.UserRoleEntity;
import bg.softuni.model.entities.enums.UserRole;
import bg.softuni.model.service.UserRegistrationServiceModel;
import bg.softuni.repository.UserRepository;
import bg.softuni.repository.UserRoleRepository;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final DimoDBUserService dimoDBUserService;

    public UserServiceImpl(UserRoleRepository userRoleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder, ModelMapper modelMapper, DimoDBUserService dimoDBUserService) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.dimoDBUserService = dimoDBUserService;
    }


    @Override
    public void seedUsers() {

        if (userRepository.count() == 0) {

            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));

            UserEntity admin = new UserEntity().
                    setUsername("admin").
                    setFullname("Admin Adminov").
                    setPassword(passwordEncoder.encode("123")).
                    setEmail("admin@gmail.com");
            UserEntity user = new UserEntity().
                    setUsername("user").
                    setFullname("User Userov").
                    setPassword(passwordEncoder.encode("123")).
                    setEmail("user@gmail.com");
            admin.setRoles(List.of(adminRole, userRole));
            user.setRoles(List.of(userRole));

            userRepository.saveAll(List.of(admin, user));
        }
    }

    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel serviceModel) {
        UserEntity newUser = modelMapper.map(serviceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(serviceModel.getPassword()));

        UserRoleEntity userRole = userRoleRepository.
                findByRole(UserRole.USER).
                orElseThrow(() -> new IllegalStateException("User role not found. Please seed the roles."));
        newUser.addRole(userRole);
        newUser = userRepository.save(newUser);

        UserDetails principal = dimoDBUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean isThisUsernameAlreadyExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


}

