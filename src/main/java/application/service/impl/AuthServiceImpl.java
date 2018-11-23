package application.service.impl;


import application.config.JwtTokenUtil;
import application.entity.User;
import application.entity.userSecurity.JwtUser;
import application.entity.userSecurity.Role;
import application.repository.UserRepository;
import application.service.AuthService;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public User register(User userToAdd) {
        final String email = userToAdd.getEmail();
        if (userRepository.findByEmail(email) != null)
            return null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String password = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(password));
        userToAdd.setRole(Role.USER);
        return userRepository.save(userToAdd);
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
}
