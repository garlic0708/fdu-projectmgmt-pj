package application.controller;

import application.controller.event.OnRegistrationCompleteEvent;
import application.controller.security.JwtAuthenticationRequest;
import application.controller.security.JwtAuthenticationResponse;
import application.entity.forms.ResultMessage;
import application.entity.User;
import application.entity.userSecurity.UpdatePasswordForm;
import application.entity.userSecurity.VerificationToken;
import application.exception.RegisterException;
import application.exception.UpdatePasswordException;
import application.exception.VerificationException;
import application.service.AuthService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Value(("${resultMessage.error.auth.login}"))
    private String loginErrorMessage;
    @Value("${resultMessage.success.auth.register}")
    private String registerSuccessMessage;
    @Value("${resultMessage.success.auth.registrationConfirm}")
    private String registrationConfirmSuccessMessage;
    @Value("${resultMessage.success.auth.update}")
    private String updateSuccessMessage;

    private AuthService authService;
    private final ApplicationEventPublisher eventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public AuthController(AuthService authService, ApplicationEventPublisher eventPublisher) {
        this.authService = authService;
        this.eventPublisher = eventPublisher;
    }


    @RequestMapping(value = "${jwt.route.authentication.login}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) {
        try {
            final String token = authService.login(authenticationRequest.getEmail(), authenticationRequest.getPassword());

            // Return the token
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new ResultMessage(loginErrorMessage));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public ResponseEntity<?> register(javax.servlet.http.HttpServletRequest request, @RequestBody User addedUser) {
        LOGGER.info(addedUser.toString());
        try {
            VerificationToken verificationToken = authService.register(addedUser);
            LOGGER.info("start authenticating");
            LOGGER.info(verificationToken.toString());
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (request, verificationToken, request.getLocale()));
            LOGGER.info("complete publish event");
            ResponseEntity body = ResponseEntity.ok().body(new ResultMessage(registerSuccessMessage));
            LOGGER.info(body.toString());
            return body;
        } catch (RegisterException e) {
            return ResponseEntity.status(422).body(new ResultMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.registrationConfirm}", method = RequestMethod.GET)
    public ResponseEntity<?> registrationConfirm(@RequestParam("token") String token) {
        try {
            authService.registrationConfirm(token);
            return ResponseEntity.ok(new ResultMessage(registrationConfirmSuccessMessage));
        } catch (VerificationException e) {
            return ResponseEntity.status(400).body(new ResultMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.updatePass}", method = RequestMethod.POST)
    public ResponseEntity<?> updatePass(@RequestBody UpdatePasswordForm upf) {
        try {
            authService.updatePassword(upf, false);
            return ResponseEntity.ok(new ResultMessage(updateSuccessMessage));
        } catch (UpdatePasswordException e) {
            return ResponseEntity.status(423).body(new ResultMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.resetPass}", method = RequestMethod.POST)
    public ResponseEntity<?> resetPass(@RequestBody UpdatePasswordForm upf) {
        try {
            authService.updatePassword(upf, true);
            return ResponseEntity.ok(new ResultMessage("Reset password success"));
        } catch (UpdatePasswordException e) {
            return ResponseEntity.status(423).body(new ResultMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.requestResetPass}", method = RequestMethod.PUT)
    public ResponseEntity<?> requestResetPass(@RequestParam("email") String email) {
        if (authService.reset(email) != null)
            return ResponseEntity.ok().body(new ResultMessage("Request success"));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResultMessage("No such user"));
    }
}