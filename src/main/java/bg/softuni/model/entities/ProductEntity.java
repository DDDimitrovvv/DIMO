package bg.softuni.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "manufactureDate")
    private LocalDate manufactureDate;

    @Column(name = "color", nullable = false)
    private String color;

    @ManyToOne()
    private CategoryEntity categoryEntity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "warranty")
    private int warranty;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    public ProductEntity() {
    }


    public String getBrand() {
        return brand;
    }

    public ProductEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }


    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public ProductEntity setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ProductEntity setColor(String color) {
        this.color = color;
        return this;
    }


    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public ProductEntity setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
        return this;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }


    public int getWarranty() {
        return warranty;
    }

    public ProductEntity setWarranty(int warranty) {
        this.warranty = warranty;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ProductEntity setDetails(String details) {
        this.details = details;
        return this;
    }
}
