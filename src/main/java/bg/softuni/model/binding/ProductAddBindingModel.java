package bg.softuni.model.binding;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductAddBindingModel {

    @NotBlank(message = "The brand cannot be empty!")
    @Size(min = 2, max = 20, message = "The brand name must contains between two and twenty characters.")
    private String brand;

    @NotBlank(message = "The model cannot be empty!")
    @Size(min = 3, max = 20, message = "The model name must contains between three and twenty characters.")
    private String model;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "The date cannot be in the future.")
    private LocalDate manufactureDate;

    @NotBlank(message = "The color cannot be empty!")
    @Size(min = 3, max = 20, message = "The color must contains between three and twenty characters.")
    private String color;

    @DecimalMin(value = "0", message = "The price must be bigger than a zero!")
    private BigDecimal price;

    private int warranty;

    @Size(min = 5, message = "The product description must be at least five characters.")
    private String details;

    @NotNull
    private MultipartFile imageUrl;

    @NotBlank(message = "You must select a category!")
    private String categoryName;

    public ProductAddBindingModel() {
    }

    public String getBrand() {
        return brand;
    }

    public ProductAddBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ProductAddBindingModel setModel(String model) {
        this.model = model;
        return this;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public ProductAddBindingModel setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ProductAddBindingModel setColor(String color) {
        this.color = color;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getWarranty() {
        return warranty;
    }

    public ProductAddBindingModel setWarranty(int warranty) {
        this.warranty = warranty;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ProductAddBindingModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public ProductAddBindingModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ProductAddBindingModel setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
