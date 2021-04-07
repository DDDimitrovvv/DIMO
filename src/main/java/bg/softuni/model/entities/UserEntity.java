package bg.softuni.model.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Expose
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Expose
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Expose
    @Column(name = "password", nullable = false)
    private String password;

    @Expose
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles = new ArrayList<>();


    public UserEntity() {
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public UserEntity setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public UserEntity setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public UserEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    //external logic
    public UserEntity addRole(UserRoleEntity roleEntity){
        this.roles.add(roleEntity);
        return this;
    }
}
