package application.controller;

import application.component.JwtTokenUtil;
import application.entity.forms.ResultMessage;
import application.entity.User;
import application.exception.JoinEventException;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@RestController
public class UserController {
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public UserController(UserService userService,
                          JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return (userService.getAllUsers());
    }

    @RequestMapping(value = "/users2", method = RequestMethod.GET)
    public List<User> getUsers2() {
        return (userService.getAllUsers());
    }

    @RequestMapping(value = "${api.user.join}/{eid}", method = RequestMethod.PUT)
    public ResponseEntity<?> joinEvent(HttpServletRequest request,@PathVariable("eid") String eid) {
        String authHeader = request.getHeader(this.tokenHeader);
        final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
        String email = jwtTokenUtil.getUsernameFromToken(authToken);
        User user = userService.getByEmail(email).orElse(null);

        try {
            userService.joinEvent(user.getuId(), Integer.parseInt(eid));
            return ResponseEntity.ok().body(new ResultMessage("Join event success"));
        }
        catch (JoinEventException e) {
            return ResponseEntity.status(425).body(new ResultMessage(e.getMessage()));
        }

    }
}
