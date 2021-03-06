package bg.softuni.scheduletasks;

import bg.softuni.service.LogService;
import bg.softuni.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearLogFromDB {
    private final LogService logService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public ClearLogFromDB(LogService logService) {
        this.logService = logService;
    }

    @Scheduled(cron = "0 0 0 * * *") /* every 24 hours */
    public void clearDBFromOldLogs(){
        logService.deleteAllLogsEntities();
        LOGGER.info("All logs have been reset!");
    }
}
