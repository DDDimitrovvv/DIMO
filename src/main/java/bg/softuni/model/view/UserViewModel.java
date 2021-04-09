package bg.softuni.model.view;


import java.time.LocalDateTime;

public class UserViewModel {
    private Long id;
    private String username;
    private String fullname;
    private String password;
    private String avatarUrl;
    private String description;
    private LocalDateTime registerDate;
    private String roles;

    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public UserViewModel setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserViewModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public UserViewModel setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UserViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public UserViewModel setRoles(String roles) {
        this.roles = roles;
        return this;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public UserViewModel setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
        return this;
    }
}
