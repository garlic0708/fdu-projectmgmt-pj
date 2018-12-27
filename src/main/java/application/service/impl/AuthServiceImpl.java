package application.service.impl;


import application.component.JwtTokenUtil;
import application.component.RegistrationListener;
import application.entity.User;
import application.entity.userSecurity.PasswordReset;
import application.entity.userSecurity.Role;
import application.entity.userSecurity.UpdatePasswordForm;
import application.entity.userSecurity.VerificationToken;
import application.exception.RegisterException;
import application.exception.UpdatePasswordException;
import application.exception.VerificationException;
import application.repository.ResetPasswordRepository;
import application.repository.UserRepository;
import application.repository.VerificationTokenRepository;
import application.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import java.util.Random;


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
    private final JavaMailSender mailSender;
    private ResetPasswordRepository resetPasswordRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${emailList}")
    private String[] emails;
    @Value("${defaultNickname}")
    private String defaultNickname;
    @Value("${verifyCodes}")
    private String verifyCodes;
    @Value("${verifySize}")
    private int verifySize;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${messages.resetPass}")
    private String resetPassMessage;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository,
            VerificationTokenRepository verificationTokenRepository,
            ResetPasswordRepository resetPasswordRepository,
            JavaMailSender mailSender) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.resetPasswordRepository = resetPasswordRepository;
        this.mailSender = mailSender;
    }

    @Override
    public VerificationToken register(User userToAdd) throws RegisterException {
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
        else if (userRepository.findByEmail(verificationToken.getEmail()).isPresent()) {
            throw new VerificationException("You have registered");
        } else {
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
    public void updatePassword(UpdatePasswordForm upf, boolean isReset) throws UpdatePasswordException {
        final String email = upf.getEmail();
        final String oldPass = upf.getOldPassword();
        final String newPass = upf.getNewPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!userRepository.findByEmail(email).isPresent())
            throw new UpdatePasswordException("No such user");
        User user = userRepository.findByEmail(email).get();

        PasswordReset passwordReset = null;
        if (isReset) { //如果是reset
            passwordReset = resetPasswordRepository.findByUid(user.getuId());
            if (passwordReset == null || !oldPass.equals(passwordReset.getCode()))
                throw new UpdatePasswordException("Verify code is wrong");
        } else {
            if (!oldPass.equals(user.getPassword()))
                throw new UpdatePasswordException("Password is wrong");
        }

        if (newPass == null || newPass.equals(""))
            throw new UpdatePasswordException("New password can not be empty");
        user.setPassword(encoder.encode(newPass));
        userRepository.save(user);
        if (isReset) {
            resetPasswordRepository.delete(passwordReset);
        }
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

    @Override
    public User reset(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            PasswordReset passwordReset = resetPasswordRepository.findByUid(user.getuId());
            if (passwordReset != null)
                resetPasswordRepository.delete(passwordReset);
            passwordReset = new PasswordReset();
            passwordReset.setUid(user.getuId());
            String code = generateVerifyCode(verifySize, verifyCodes);
            passwordReset.setCode(code);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(username);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("Reset password");
            simpleMailMessage.setText(resetPassMessage.replace("%code%", code));
            LOGGER.info(simpleMailMessage.toString());
            mailSender.send(simpleMailMessage);

            resetPasswordRepository.save(passwordReset);
        }
        return user;
    }


    private String generateVerifyCode(int verifySize, String sources) {
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    private boolean validateEmailFormat(String email) {
        return (email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"));
    }

    private boolean validateEmailSource(String email) {
        for (String sourceEmail : emails) {
            if (email.endsWith(sourceEmail))
                return true;
        }
        return false;
    }
}
