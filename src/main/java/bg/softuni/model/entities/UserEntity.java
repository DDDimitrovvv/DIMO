package bg.softuni.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles = new ArrayList<>();

    @OneToMany
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany
    private List<StoryEntity> stories = new ArrayList<>();

    public UserEntity() {
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

    public List<ProductEntity> getProducts() {
        return products;
    }

    public UserEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }

    public List<StoryEntity> getStories() {
        return stories;
    }

    public UserEntity setStories(List<StoryEntity> stories) {
        this.stories = stories;
        return this;
    }

    public UserEntity addRole(UserRoleEntity roleEntity){
        this.roles.add(roleEntity);
        return this;
    }
}
