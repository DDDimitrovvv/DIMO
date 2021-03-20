package bg.softuni.service;

import bg.softuni.model.service.ProductAddServiceModel;
import bg.softuni.model.view.ProductViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductViewModel> getListWithAllProducts();

    void addProduct(ProductAddServiceModel productAddServiceModel) throws IOException;

    Map<String, Integer> availableProductInDB();

}
