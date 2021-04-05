package bg.softuni.service.impl;

import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.service.ProfileServiceModel;
import bg.softuni.model.view.ProductViewModel;
import bg.softuni.scheduletasks.ActiveUserStore;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.UserRoleEntity;
import bg.softuni.model.entities.enums.UserRole;
import bg.softuni.model.service.UserRegistrationServiceModel;
import bg.softuni.model.view.UserViewModel;
import bg.softuni.repository.UserRepository;
import bg.softuni.repository.UserRoleRepository;
import bg.softuni.service.CloudinaryService;
import bg.softuni.service.ProductService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final CloudinaryService cloudinaryService;


    public UserServiceImpl(UserRoleRepository userRoleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper,
                           DimoDBUserService dimoDBUserService,
                           ActiveUserStore activeUserStore,
                           CloudinaryService cloudinaryService) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.dimoDBUserService = dimoDBUserService;
        this.activeUserStore = activeUserStore;
        this.cloudinaryService = cloudinaryService;
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
                    setPassword(passwordEncoder.encode("123456")).
                    setAvatarUrl("https://res.cloudinary.com/dsrmaoof8/image/upload/v1617040599/maxresdefault_dbs08u.jpg");
            UserEntity user = new UserEntity().
                    setUsername("user@gmail.com").
                    setFullname("User Userov").
                    setPassword(passwordEncoder.encode("123456")).
                    setAvatarUrl("https://res.cloudinary.com/dsrmaoof8/image/upload/v1617040348/profileAvatar_p85qvd.png");
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
        newUser.
                addRole(userRole).
                setAvatarUrl("https://res.cloudinary.com/dsrmaoof8/image/upload/v1617040348/profileAvatar_p85qvd.png");
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
    public UserViewModel getCurrentUserViewModel() {
        return modelMapper.map(this.getCurrentUser(), UserViewModel.class);
    }

    @Override
    public boolean validateUserAccess(Long id) {
        return this.getCurrentUser().getId() == id || this.getCurrentUser().getUsername().equals("admin@gmail.com");
    }

    @Override
    public void updateUser(ProfileServiceModel profileServiceModel, Long id) throws IOException {
        String avatarUrl = this.getCurrentUser().getAvatarUrl();

        UserEntity userEntity = modelMapper.map(profileServiceModel, UserEntity.class);

        if (!profileServiceModel.getAvatarUrl().isEmpty()) {
            MultipartFile img = profileServiceModel.getAvatarUrl();
            avatarUrl = cloudinaryService.uploadImage(img);
        }

        userEntity.
                setAvatarUrl(avatarUrl).
                setRoles(this.getCurrentUser().getRoles()).
                setPassword(passwordEncoder.encode(profileServiceModel.getPassword())).
                setId(id);

        userRepository.save(userEntity);
    }

    @Override
    public boolean checkIfUserIsRootAdmin() {
        return this.getCurrentUser().getUsername().equals("admin@gmail.com");
    }

    @Override
    public boolean checkIsAdmin() {
        boolean isAdmin = false;

        for ( UserRoleEntity role : this.getCurrentUser().getRoles() ){
            if (role.getRole().equals(UserRole.ADMIN)) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        userRepository.deleteById(id);
    }

    @Override
    public boolean findUserById(Long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public UserViewModel getViewUserById(Long id) {
        UserViewModel userEntModel = new UserViewModel();
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity != null){
            userEntModel = modelMapper.map(userEntity, UserViewModel.class);
        }
        return userEntModel;
    }

    @Override
    public List<UserViewModel> getAllUsersFromDB() {
        List<UserEntity> allUserEntities = userRepository.findAll();
        List<UserViewModel> userViewModelList = new ArrayList<>();
        for ( UserEntity userEntity : allUserEntities ){
            UserViewModel userViewModel = modelMapper.map(userEntity, UserViewModel.class);
            StringBuilder roles = new StringBuilder();
            for ( UserRoleEntity role : userEntity.getRoles() ){
                roles.append(role.getRole().name()).append("; ");
            }
            userViewModel.setRoles(roles.toString().trim());
            userViewModelList.add(userViewModel);
        }

        return userViewModelList;
    }
}

