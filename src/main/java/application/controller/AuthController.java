package application.controller;

import application.controller.event.OnRegistrationCompleteEvent;
import application.controller.security.JwtAuthenticationRequest;
import application.controller.security.JwtAuthenticationResponse;
import application.entity.ResultMessage;
import application.entity.User;
import application.entity.userSecurity.UpdatePasswordForm;
import application.entity.userSecurity.VerificationToken;
import application.exception.RegisterException;
import application.exception.UpdatePasswordException;
import application.exception.VerificationExecption;
import application.service.AuthService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        try {
            final String token = authService.login(authenticationRequest.getEmail(), authenticationRequest.getPassword());

            // Return the token
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new ResultMessage("Bad email or password"));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public ResponseEntity<?> register(javax.servlet.http.HttpServletRequest request, @RequestBody User addedUser) throws AuthenticationException {
        LOGGER.info(addedUser.toString());
        try {
            VerificationToken verificationToken = authService.register(addedUser);
            LOGGER.info("start authenticating");
            LOGGER.info(verificationToken.toString());
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (request, verificationToken, request.getLocale()));
            LOGGER.info("complete publish event");
            ResponseEntity body = ResponseEntity.ok().body(new ResultMessage("register success"));
            LOGGER.info(body.toString());
            return body;
        }
        catch (RegisterException e) {
            return ResponseEntity.status(422).body(new ResultMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.registrationConfirm}", method = RequestMethod.GET)
    public ResponseEntity<?> registrationConfirm(@RequestParam("token") String token) {
        try {
            authService.registrationConfirm(token);
            return ResponseEntity.ok(new ResultMessage("registration confirm success"));
        }
        catch (VerificationExecption e) {
            return ResponseEntity.status(424).body(new ResultMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.updatePass}", method = RequestMethod.POST)
    public ResponseEntity<?> updatePass(@RequestBody UpdatePasswordForm upf) throws UpdatePasswordException {
        try {
            authService.updatePassword(upf);
            return ResponseEntity.ok(new ResultMessage("update success"));
        }
        catch (UpdatePasswordException e) {
            return ResponseEntity.status(423).body(new ResultMessage(e.getMessage()));
        }
    }
}