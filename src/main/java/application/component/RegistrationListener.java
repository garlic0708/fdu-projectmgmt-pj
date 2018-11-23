package application.component;

import application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import application.controller.event.OnRegistrationCompleteEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);

    private final MessageSource messages;

    @Autowired
    public RegistrationListener(@Qualifier("messageSource") MessageSource messages) {
        this.messages = messages;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();

        String email = user.getEmail();
        String subject = "Registration Confirmation";

        String message = messages.getMessage("register.email", new Object[]{null},
                event.getLocale());


        LOGGER.info(email.toString());
    }
}
