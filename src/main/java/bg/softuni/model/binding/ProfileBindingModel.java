package bg.softuni.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfileBindingModel {

    @Email(message = "The email must be valid!")
    private String username;

    @NotBlank(message = "The full name cannot be empty!")
    @Size(min = 5, max = 30, message = "The full name must be between five and thirty characters.")
    private String fullname;

    @NotBlank(message = "The password cannot be empty!")
    @Size(min = 5, max = 20, message = "The password must be between five and twenty characters.")
    private String password;

    private MultipartFile avatarUrl;

    private String description;

    public ProfileBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public ProfileBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public ProfileBindingModel setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ProfileBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public MultipartFile getAvatarUrl() {
        return avatarUrl;
    }

    public ProfileBindingModel setAvatarUrl(MultipartFile avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProfileBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
