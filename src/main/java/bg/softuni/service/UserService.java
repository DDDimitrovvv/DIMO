package bg.softuni.service;

import bg.softuni.model.service.UserRegistrationServiceModel;

public interface UserService {

  void seedUsers();

  void registerAndLoginUser(UserRegistrationServiceModel serviceModel);

}
