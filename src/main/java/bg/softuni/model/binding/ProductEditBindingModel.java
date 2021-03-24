package bg.softuni.model.binding;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductEditBindingModel {

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

    @Min(0)
    private int warranty;

    @Size(min = 5, max = 650, message = "The product description must be at least five characters.")
    private String details;

    @NotNull
    private MultipartFile imageUrl;

    @NotBlank(message = "You must select a category!")
    private String categoryName;

    public ProductEditBindingModel() {
    }

    public String getBrand() {
        return brand;
    }

    public ProductEditBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ProductEditBindingModel setModel(String model) {
        this.model = model;
        return this;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public ProductEditBindingModel setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ProductEditBindingModel setColor(String color) {
        this.color = color;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEditBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getWarranty() {
        return warranty;
    }

    public ProductEditBindingModel setWarranty(int warranty) {
        this.warranty = warranty;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ProductEditBindingModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public ProductEditBindingModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ProductEditBindingModel setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
