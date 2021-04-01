package bg.softuni.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "archived_products")
public class ArchivedProductEntity extends BaseEntity {

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "warranty")
    private int warranty;

    @Column(name = "details", columnDefinition = "TEXT", nullable = false)
    private String details;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "purchased_user_id", nullable = false)
    private Long purchasedUserId;

    @Column(name = "purchased_user_username", nullable = false)
    private String purchasedUsername;

    @Column(name = "purchased_date_and_time", nullable = false)
    private LocalDateTime purchasedDateAndTime;

    @ManyToOne()
    private CategoryEntity categoryEntity;

    @ManyToOne()
    private UserEntity userEntity;


    public ArchivedProductEntity() {
    }

    public String getBrand() {
        return brand;
    }

    public ArchivedProductEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ArchivedProductEntity setModel(String model) {
        this.model = model;
        return this;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public ArchivedProductEntity setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ArchivedProductEntity setColor(String color) {
        this.color = color;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ArchivedProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getWarranty() {
        return warranty;
    }

    public ArchivedProductEntity setWarranty(int warranty) {
        this.warranty = warranty;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ArchivedProductEntity setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArchivedProductEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Long getPurchasedUserId() {
        return purchasedUserId;
    }

    public ArchivedProductEntity setPurchasedUserId(Long purchasedUserId) {
        this.purchasedUserId = purchasedUserId;
        return this;
    }

    public LocalDateTime getPurchasedDateAndTime() {
        return purchasedDateAndTime;
    }

    public ArchivedProductEntity setPurchasedDateAndTime(LocalDateTime purchasedDateAndTime) {
        this.purchasedDateAndTime = purchasedDateAndTime;
        return this;
    }

    public String getPurchasedUsername() {
        return purchasedUsername;
    }

    public ArchivedProductEntity setPurchasedUsername(String purchasedUsername) {
        this.purchasedUsername = purchasedUsername;
        return this;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public ArchivedProductEntity setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
        return this;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public ArchivedProductEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }
}
