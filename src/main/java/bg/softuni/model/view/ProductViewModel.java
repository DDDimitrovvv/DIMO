package bg.softuni.model.view;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductViewModel {

    private String brand;
    private String model;
    private LocalDate manufactureDate;
    private String color;
    private BigDecimal price;
    private int warranty;
    private String details;
    private String imageUrl;
    private String categoryName;

    public ProductViewModel() {
    }

    public ProductViewModel(String brand, String model, LocalDate manufactureDate, String color, BigDecimal price, int warranty, String details, String imageUrl, String categoryName) {
        this.brand = brand;
        this.model = model;
        this.manufactureDate = manufactureDate;
        this.color = color;
        this.price = price;
        this.warranty = warranty;
        this.details = details;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }

    public String getBrand() {
        return brand;
    }

    public ProductViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ProductViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public ProductViewModel setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ProductViewModel setColor(String color) {
        this.color = color;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getWarranty() {
        return warranty;
    }

    public ProductViewModel setWarranty(int warranty) {
        this.warranty = warranty;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ProductViewModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ProductViewModel setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
