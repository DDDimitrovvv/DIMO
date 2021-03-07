package bg.softuni.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles = new ArrayList<>();

    @OneToMany
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany
    private List<StoryEntity> stories = new ArrayList<>();

    public UserEntity() {
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
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
}
