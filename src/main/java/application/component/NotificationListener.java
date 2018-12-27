package application.component;

import application.controller.event.OnNewNotificationEvent;
import application.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener implements
        ApplicationListener<OnNewNotificationEvent> {

    private static final Log LOGGER = LogFactory.getLog(NotificationListener.class);

    @Value("${webSocket.prefix}")
    private String prefix;

    @Value("${webSocket.newNotif}")
    private String subscriptionUrl;

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;

    @Autowired
    public NotificationListener(SimpMessagingTemplate simpMessagingTemplate,
                                UserService userService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(OnNewNotificationEvent event) {
        sendNotification(event);
    }

    private void sendNotification(OnNewNotificationEvent event) {
        LOGGER.debug(String.format("Sending notification to user %d", event.getMessage().getReceiver()));
        simpMessagingTemplate.convertAndSendToUser(
                userService.getByUid(event.getMessage().getReceiver()).getEmail(),
                String.format("%s%s", prefix, subscriptionUrl),
                event.getMessage()
        );
    }
}
