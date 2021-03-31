package bg.softuni.service;

import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.enums.UserRole;
import bg.softuni.model.service.ProfileServiceModel;
import bg.softuni.model.service.UserRegistrationServiceModel;
import bg.softuni.model.view.StoryViewModel;
import bg.softuni.model.view.UserViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegistrationServiceModel serviceModel);

    boolean isThisUsernameAlreadyExists(String username);

    String currentUserFullName() throws Exception;

    UserEntity getCurrentUser() throws Exception;

    int getCountOfAllLoggedUsers();

    int getCountOfAllUsersInDB();

    int getCountOfAllRegisteredUsers();

    List<String> getAllUsernameList() throws Exception;

    void changeRole(String username, String role) throws Exception;

    UserViewModel getCurrentUserViewModel();

    void deleteUser(long id);

    boolean validateUserAccess(Long id);

    void updateUser(ProfileServiceModel profileServiceModel, Long id) throws IOException;
}
