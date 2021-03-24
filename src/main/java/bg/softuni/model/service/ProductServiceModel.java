package bg.softuni.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductServiceModel {

    private String brand;
    private String model;
    private LocalDate manufactureDate;
    private String color;
    private BigDecimal price;
    private int warranty;
    private String details;
    private MultipartFile imageUrl;
    private String categoryName;

    public ProductServiceModel() {
    }


    public String getBrand() {
        return brand;
    }

    public ProductServiceModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ProductServiceModel setModel(String model) {
        this.model = model;
        return this;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public ProductServiceModel setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ProductServiceModel setColor(String color) {
        this.color = color;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getWarranty() {
        return warranty;
    }

    public ProductServiceModel setWarranty(int warranty) {
        this.warranty = warranty;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ProductServiceModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public ProductServiceModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ProductServiceModel setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
