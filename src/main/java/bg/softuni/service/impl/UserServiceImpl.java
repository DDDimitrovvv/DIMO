package bg.softuni.service.impl;

import bg.softuni.model.scheduletasks.ActiveUserStore;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final DimoDBUserService dimoDBUserService;
    private final ActiveUserStore activeUserStore;


    public UserServiceImpl(UserRoleRepository userRoleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper,
                           DimoDBUserService dimoDBUserService,
                           ActiveUserStore activeUserStore) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.dimoDBUserService = dimoDBUserService;
        this.activeUserStore = activeUserStore;
    }


    @Override
    public void seedUsers() {

        if (userRepository.count() == 0) {

            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));

            UserEntity admin = new UserEntity().
                    setUsername("admin@gmail.com").
                    setFullname("Admin Adminov").
                    setPassword(passwordEncoder.encode("123456"));
            UserEntity user = new UserEntity().
                    setUsername("user@gmail.com").
                    setFullname("User Userov").
                    setPassword(passwordEncoder.encode("123456"));
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

        activeUserStore.setRegisteredUsersCounter(1);

        UserDetails principal = dimoDBUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        activeUserStore.setLoggedUsersCounter(1);
    }

    @Override
    public boolean isThisUsernameAlreadyExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public String currentUserFullName() {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new IllegalArgumentException("User not found!"));
        String currentUserFullName = "";
        if (currentUser != null) {
            currentUserFullName = currentUser.getFullname();
        }

        return currentUserFullName;
    }

    @Override
    public UserEntity getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(currentUsername).orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }

    @Override
    public int getCountOfAllLoggedUsers() {
        return activeUserStore.getLoggedUsersCounter();
    }

    @Override
    public int getCountOfAllUsersInDB() {
        return userRepository.findAll().size();
    }

    @Override
    public int getCountOfAllRegisteredUsers() {
        return activeUserStore.getRegisteredUsersCounter();
    }

    @Override
    public List<String> getAllUsernameList() {
        List<String> allUsernames = userRepository.findAllUsernames();
        List<String> usernameList = new ArrayList<>();

        for ( String username : allUsernames ){
            if (!username.equalsIgnoreCase(getCurrentUser().getUsername()) && !username.equals("admin@gmail.com")) {
                usernameList.add(username);
            }
        }

        return usernameList;
    }

    @Override
    public void changeRole(String username, String role) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        // split String to List
        List<String> roles = Arrays.asList(role.split("\\s*,\\s*"));
        // map List with Strings to UserRole
        List<UserRole> userRoles = roles.stream().map(r -> UserRole.valueOf(r.toUpperCase())).collect(Collectors.toList());
        // create an empty List with UserRoleEntity
        List<UserRoleEntity> userRoleEntities = new ArrayList<>();

        for ( UserRole userRole : userRoles ){
            UserRoleEntity userRoleEntity = userRoleRepository.findByRole(userRole).orElseThrow(() -> new IllegalArgumentException("Not found user with " + username + "!"));
            if (userRoleEntity != null) {
                userRoleEntities.add(userRoleEntity);
            }
        }

        if (user.isPresent()) {
            user.get().setRoles(userRoleEntities);
            userRepository.save(user.get());
        }
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}

