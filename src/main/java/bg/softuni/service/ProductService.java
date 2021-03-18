package bg.softuni.service;

import bg.softuni.model.service.ProductAddServiceModel;
import bg.softuni.model.view.ProductViewModel;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductViewModel> getListWithAllProducts();

    void addProduct(ProductAddServiceModel productAddServiceModel) throws IOException;
}
