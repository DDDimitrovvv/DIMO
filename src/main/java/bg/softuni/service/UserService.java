package bg.softuni.service;

import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.entities.enums.UserRole;
import bg.softuni.model.service.UserRegistrationServiceModel;

import java.util.List;
import java.util.Map;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegistrationServiceModel serviceModel);

    boolean isThisUsernameAlreadyExists(String username);

    String currentUserFullName();

    UserEntity getCurrentUser();

    int getCountOfAllLoggedUsers();

    int getCountOfAllUsersInDB();

    int getCountOfAllRegisteredUsers();

    List<String> getAllUsernameList();

    void changeRole(String username, String role);

}
