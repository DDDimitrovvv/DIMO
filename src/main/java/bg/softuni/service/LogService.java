package bg.softuni.service;

import bg.softuni.model.view.LogViewModel;

import java.util.List;

public interface LogService {
    void createLog(String actionPage, Long productId) throws Exception;

    List<LogViewModel> getAllLogsOfUsers();
}
