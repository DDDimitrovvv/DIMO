package bg.softuni.service;

import bg.softuni.model.binding.ProductBindingModel;

import java.util.List;

public interface ProductService {
    List<ProductBindingModel> getListWithAllProducts();
}
