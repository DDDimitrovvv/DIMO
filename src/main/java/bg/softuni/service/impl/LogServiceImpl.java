package bg.softuni.service.impl;

import bg.softuni.model.entities.LogEntity;
import bg.softuni.model.entities.ProductEntity;
import bg.softuni.model.entities.UserEntity;
import bg.softuni.model.view.LogViewModel;
import bg.softuni.repository.LogRepository;
import bg.softuni.service.LogService;
import bg.softuni.service.ProductService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository,
                          ProductService productService,
                          UserService userService, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.productService = productService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void createLog(String actionPage, Long productId) throws Exception {

        ProductEntity productEntity = productService.findProductEntityById(productId);
        UserEntity currentUser = userService.getCurrentUser();
        LogEntity logEntity = new LogEntity().
                setUserEntity(currentUser).
                setProductEntity(productEntity).
                setLocalDateTime(LocalDateTime.now()).
                setActionPage(actionPage);

        logRepository.save(logEntity);
    }

    @Override
    public List<LogViewModel> getAllLogsOfUsers() {
        return logRepository.
                findAll().
                stream().
                map(logEntity -> {
                    LogViewModel logViewModel = modelMapper.map(logEntity, LogViewModel.class);
                    logViewModel.
                            setUsername(logEntity.getUserEntity().getUsername()).
                            setProductBrand(logEntity.getProductEntity().getBrand()).
                            setProductModel(logEntity.getProductEntity().getModel());
                    return logViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteAllLogsEntities() {
        logRepository.deleteAll();
    }

    @Override
    public void deleteAllLogsForProductWithId(Long id) {
        List<LogEntity> logEntities = logRepository.findAllByProductEntity_Id(id);
        logEntities.forEach(logEntity -> logRepository.deleteById(logEntity.getId()));
    }
}
