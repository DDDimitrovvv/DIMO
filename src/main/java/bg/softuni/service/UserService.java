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

    String currentUserFullName() throws Exception;

    UserEntity getCurrentUser() throws Exception;

    int getCountOfAllLoggedUsers();

    int getCountOfAllUsersInDB();

    int getCountOfAllRegisteredUsers();

    List<String> getAllUsernameList() throws Exception;

    void changeRole(String username, String role) throws Exception;

    void deleteUser(long id);
}
