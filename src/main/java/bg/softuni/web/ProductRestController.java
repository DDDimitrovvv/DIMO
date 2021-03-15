package bg.softuni.web;

import bg.softuni.model.binding.ProductBindingModel;
import bg.softuni.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductRestController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductRestController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public List<ProductBindingModel> findAll(){
        return productService.getListWithAllProducts();
    }
}
