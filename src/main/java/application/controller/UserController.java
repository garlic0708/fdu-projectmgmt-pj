package application.controller;

import application.component.JwtTokenUtil;
import application.entity.forms.ResultMessage;
import application.entity.User;
import application.exception.JoinEventException;
import application.service.EventService;
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
    private EventService eventService;
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public UserController(UserService userService,
                          EventService eventService,
                          JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.eventService = eventService;
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
    public ResponseEntity<?> joinEvent(HttpServletRequest httpServletRequest,@PathVariable("eid") String eid) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        try {
            userService.joinEvent(user.getuId(), Integer.parseInt(eid));
            return ResponseEntity.ok().body(new ResultMessage("Join event success"));
        }
        catch (JoinEventException e) {
            return ResponseEntity.status(425).body(new ResultMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "${api.user.joined}", method = RequestMethod.GET)
    public ResponseEntity<?> getJoinedEvent(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        return ResponseEntity.ok().body(eventService.getEventsJoined(user.getuId()));
    }

    @RequestMapping(value = "${api.user.released}", method = RequestMethod.GET)
    public ResponseEntity<?> getReleasedEvent(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        return ResponseEntity.ok().body(eventService.getEventsReleased(user.getuId()));
    }

    @RequestMapping(value = "${api.user.released}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserDetail(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        return ResponseEntity.ok().body(userService.getByEmail(user.getEmail()));
    }
}
