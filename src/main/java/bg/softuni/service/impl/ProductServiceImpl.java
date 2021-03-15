package bg.softuni.service.impl;

import bg.softuni.model.binding.ProductBindingModel;
import bg.softuni.repository.ProductRepository;
import bg.softuni.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductBindingModel> getListWithAllProducts() {
        return productRepository.
                findAll().
                stream().
                map(productEntity -> modelMapper.map(productEntity, ProductBindingModel.class)).
                collect(Collectors.toList());
    }
}
