package application.service.impl;


import application.component.JwtTokenUtil;
import application.entity.User;
import application.entity.userSecurity.Role;
import application.entity.userSecurity.UpdatePasswordForm;
import application.entity.userSecurity.VerificationToken;
import application.exception.RegisterException;
import application.exception.UpdatePasswordException;
import application.exception.VerificationException;
import application.repository.UserRepository;
import application.repository.VerificationTokenRepository;
import application.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${emailList}")
    private String[] emails;

    @Value("${defaultNickname}")
    private String defaultNickname;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository,
            VerificationTokenRepository verificationTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public VerificationToken register(User userToAdd) throws RegisterException{
        final String email = userToAdd.getEmail();
        if (!validateEmailFormat(email))
            throw new RegisterException("Email format is wrong");
        if (!validateEmailSource(email))
            throw new RegisterException("Email domain is wrong");
        if (userRepository.findByEmail(email).isPresent())
            throw new RegisterException("Email already exists");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setEmail(userToAdd.getEmail());
        verificationToken.setPassword(encoder.encode(userToAdd.getPassword()));
        if (userToAdd.getNickname() == null || userToAdd.getNickname().equals(""))
            verificationToken.setNickname(defaultNickname);
        verificationToken.setNickname(userToAdd.getNickname());
        verificationToken.setImage(userToAdd.getImage());
        verificationToken.setVredict(100);
        verificationToken.setRole(Role.USER);
        return verificationToken;
    }

    @Override
    public void registrationConfirm(String token) throws VerificationException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null)
            throw new VerificationException("Invalid token");
        else if (verificationToken.getExpiryDate().before(Date.from(Instant.now())))
            throw new VerificationException("Token has expired");
        else if (userRepository.findByEmail(verificationToken.getEmail()) != null) {
            throw new VerificationException("You have registered");
        }
        else {
            User user = new User();
            user.setEmail(verificationToken.getEmail());
            user.setPassword(verificationToken.getPassword());
            user.setNickname(verificationToken.getNickname());
//            user.setImage(verificationToken.getImage());
//            user.setRole(Role.USER);
            user.setCredit(verificationToken.getVredict());
            verificationTokenRepository.delete(verificationToken);
            userRepository.save(user);
        }
    }

    @Override
    public void updatePassword(UpdatePasswordForm upf) throws UpdatePasswordException{
        final String email = upf.getEmail();
        final String oldPass = upf.getOldPassword();
        final String newPass = upf.getNewPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        if (!userRepository.findByEmail(email).isPresent())
            throw new UpdatePasswordException("No such user");
        User user = userRepository.findByEmail(email).get();
        if (!encoder.matches(oldPass, user.getPassword()))
            throw new UpdatePasswordException("Password is wrong");
        if (newPass == null || newPass.equals(""))
            throw new UpdatePasswordException("New password can not be empty");
        user.setPassword(encoder.encode(newPass));
        userRepository.save(user);
    }

    @Override
    public String login(String email, String password) {
        UsernamePasswordAuthenticationToken uptoken = new UsernamePasswordAuthenticationToken(email, password);
        final Authentication authentication = authenticationManager.authenticate(uptoken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        //这里要注意，有较大改动
        final String token = oldToken.substring(tokenHead.length());
        return jwtTokenUtil.refreshToken(token);

    }

    private boolean validateEmailFormat(String email) {
        return (email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"));
    }

    private boolean validateEmailSource(String email) {
        for (String sourceEmail: emails) {
            if (email.endsWith(sourceEmail))
                return true;
        }
        return false;
    }
}
