package application.controller.event;

import application.entity.Message;
import org.springframework.context.ApplicationEvent;

public class OnNewNotificationEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OnNewNotificationEvent(Message source) {
        super(source);
    }

    public Message getMessage() {
        return (Message) getSource();
    }
}
