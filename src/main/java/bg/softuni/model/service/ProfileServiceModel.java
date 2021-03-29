package bg.softuni.model.service;

import org.springframework.web.multipart.MultipartFile;

public class ProfileServiceModel {

    private String username;
    private String fullname;
    private String password;
    private MultipartFile avatarUrl;
    private String description;

    public ProfileServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public ProfileServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public ProfileServiceModel setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ProfileServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public MultipartFile getAvatarUrl() {
        return avatarUrl;
    }

    public ProfileServiceModel setAvatarUrl(MultipartFile avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProfileServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
