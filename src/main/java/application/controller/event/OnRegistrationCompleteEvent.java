package application.controller.event;

import application.entity.User;
import application.entity.userSecurity.VerificationToken;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private VerificationToken verificationToken;
    private Locale locale;
    private String url;

    public OnRegistrationCompleteEvent(HttpServletRequest request, VerificationToken verificationToken, Locale locale) {
        super(verificationToken);

        this.verificationToken = verificationToken;
        this.locale = locale;
        url = String.format("%s://%s:%d", request.getScheme(), request.getServerName(),
                request.getServerPort());
    }


    public Locale getLocale() {
        return locale;
    }

    public String getUrl() {
        return url;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }
}