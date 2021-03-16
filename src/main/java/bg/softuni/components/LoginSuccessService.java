package bg.softuni.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoginSuccessService implements ApplicationListener<AuthenticationSuccessEvent>{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginSuccessService.class);
    private final ActiveUserStore activeUserStore;

    public LoginSuccessService(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        activeUserStore.setLoggedUsersCounter(1);
        LOGGER.info("You have been logged in successfully.");
        Date loginDate = new Date();
        LOGGER.info("Login Time: " + loginDate.toString());
    }
}
