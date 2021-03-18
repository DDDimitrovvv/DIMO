package bg.softuni.service;

import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.service.UserRegistrationServiceModel;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegistrationServiceModel serviceModel);

    boolean isThisUsernameAlreadyExists(String username);

    String currentUserFullName();

    UserEntity getCurrentUser();

    int getCountOfAllLoggedUsers();

    int getCountOfAllUsersInDB();

    int getCountOfAllRegisteredUsers();
}
