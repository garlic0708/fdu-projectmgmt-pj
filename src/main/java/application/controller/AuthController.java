package application.controller;

import application.controller.security.JwtAuthenticationRequest;
import application.controller.security.JwtAuthenticationResponse;
import application.entity.User;
import application.exception.RegisterException;
import application.service.AuthService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.WebDataBinder;
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


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        try {
            final String token = authService.login(authenticationRequest.getEmail(), authenticationRequest.getPassword());

            // Return the token
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Bad email or password");
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
    public ResponseEntity<?> register(@RequestBody User addedUser) throws AuthenticationException{
        try {
            authService.register(addedUser);
            return ResponseEntity.ok("register success");
        }
        catch (RegisterException e) {
            return ResponseEntity.status(422).body(e.getMessage());
        }
    }
}