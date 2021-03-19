package bg.softuni.model.activeUser;

import bg.softuni.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class ActiveUserStore {
    private int loggedUsersCounter;
    private int registeredUsersCounter;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    public ActiveUserStore() {
        this.loggedUsersCounter = 0;
        this.registeredUsersCounter = 0;
    }

    public int getLoggedUsersCounter() {
        return loggedUsersCounter;
    }

    public ActiveUserStore setLoggedUsersCounter(int loggedUsersCounter) {
        this.loggedUsersCounter += loggedUsersCounter;
        return this;
    }

    public int getRegisteredUsersCounter() {
        return registeredUsersCounter;
    }

    public ActiveUserStore setRegisteredUsersCounter(int registeredUsersCounter) {
        this.registeredUsersCounter += registeredUsersCounter;
        return this;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetLoggedAndRegisteredUsersCount(){
        this.loggedUsersCounter = 0;
        this.registeredUsersCounter = 0;
        LOGGER.info("ALl logged and registered users are reset!");
    }

}
