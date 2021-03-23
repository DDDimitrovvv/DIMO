package bg.softuni.model.view;

import java.time.LocalDateTime;

public class LogViewModel {

    private Long id;
    private String username;
    private String productBrand;
    private String productModel;
    private LocalDateTime localDateTime;
    private String actionPage;

    public LogViewModel() {
    }

    public Long getId() {
        return id;
    }

    public LogViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LogViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public LogViewModel setProductBrand(String productBrand) {
        this.productBrand = productBrand;
        return this;
    }

    public String getProductModel() {
        return productModel;
    }

    public LogViewModel setProductModel(String productModel) {
        this.productModel = productModel;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LogViewModel setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public String getActionPage() {
        return actionPage;
    }

    public LogViewModel setActionPage(String actionPage) {
        this.actionPage = actionPage;
        return this;
    }
}
