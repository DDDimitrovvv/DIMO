package bg.softuni.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class LogEntity extends BaseEntity {

    @ManyToOne
    private UserEntity userEntity;
    @ManyToOne
    private ProductEntity productEntity;
    @Column(name = "action", nullable = false)
    private String actionPage;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime localDateTime;

    public LogEntity() {
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public LogEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public String getActionPage() {
        return actionPage;
    }

    public LogEntity setActionPage(String actionPage) {
        this.actionPage = actionPage;
        return this;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public LogEntity setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LogEntity setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }
}
