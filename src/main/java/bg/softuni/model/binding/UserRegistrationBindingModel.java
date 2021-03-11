package bg.softuni.model.binding;

import bg.softuni.model.validators.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class UserRegistrationBindingModel {

    @NotBlank(message = "The username cannot be empty!")
    @Size(min = 3, max = 20, message = "The username must be between three and twenty characters.")
    private String username;

    @Email(message = "The email must be valid!")
    private String email;

    @NotBlank(message = "The full name cannot be empty!")
    @Size(min = 5, max = 30, message = "The full name must be between five and thirty characters.")
    private String fullname;

    @NotBlank(message = "The password cannot be empty!")
    @Size(min = 5, max = 20, message = "The password must be between five and twenty characters.")
    private String password;

    @NotBlank(message = "The password cannot be empty!")
    @Size(min = 5, max = 20, message = "The password must be between five and twenty characters.")
    private String confirmPassword;

    public UserRegistrationBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegistrationBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public UserRegistrationBindingModel setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
