package application.controller;

import application.component.JwtTokenUtil;
import application.entity.forms.EventSlide;
import application.entity.forms.ResultMessage;
import application.entity.User;
import application.entity.forms.View;
import application.exception.JoinEventException;
import application.exception.UpdateUserImgException;
import application.service.EventService;
import application.service.FileService;
import application.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
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
    private FileService fileService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${imagePathTitle}")
    private String imagePathTitle;

    @Autowired
    public UserController(UserService userService,
                          EventService eventService,
                          FileService fileService,
                          JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.eventService = eventService;
        this.fileService = fileService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping(value = "${api.user.updateName}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateName(HttpServletRequest httpServletRequest,@RequestParam("nickname") String nickname) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        userService.updateName(user.getuId(), nickname);
        return ResponseEntity.ok().body(new ResultMessage("Update nickname success"));
    }

    @RequestMapping(value = "${api.user.updateImg}", method = RequestMethod.POST)
    public ResponseEntity<?> updateImg(HttpServletRequest httpServletRequest,
                                       @RequestParam("img") MultipartFile img) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

       try {
           userService.updateImg(user, img);
           return ResponseEntity.ok().body(new ResultMessage("Update img success"));
       }
        catch (UpdateUserImgException e) {
            e.printStackTrace();
            return ResponseEntity.status(429).body(new ResultMessage(e.getMessage()));
        }
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

    @RequestMapping(value = "${api.user.quit}/{eid}", method = RequestMethod.PUT)
    public ResponseEntity<?> quitEvent(HttpServletRequest httpServletRequest,@PathVariable("eid") String eid) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        userService.quitEvent(user.getuId(), Integer.parseInt(eid));
        return ResponseEntity.ok().body(new ResultMessage("Quit event success"));
    }

    @RequestMapping(value = "${api.user.checkIn}/{eid}", method = RequestMethod.PUT)
    public ResponseEntity<?> checkIn(@PathVariable("eid") String eid, @RequestParam("uid") int uid) {

        userService.checkIn(uid, Integer.parseInt(eid));
        return ResponseEntity.ok().body(new ResultMessage("CheckIn success"));
    }

    @RequestMapping(value = "${api.user.checkOut}/{eid}", method = RequestMethod.PUT)
    public ResponseEntity<?> checkOut(@PathVariable("eid") String eid, @RequestParam("uid") int uid) {

        userService.checkOut(uid, Integer.parseInt(eid));
        return ResponseEntity.ok().body(new ResultMessage("CheckOut success"));
    }

    @RequestMapping(value = "${api.user.joined}", method = RequestMethod.GET)
    @JsonView(View.SimpleEvent.class)
    public ResponseEntity<List<EventSlide>> getJoinedEvent(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        return ResponseEntity.ok().body(eventService.getEventsJoined(user.getuId()));
    }

    @RequestMapping(value = "${api.user.released}", method = RequestMethod.GET)
    @JsonView(View.SimpleEvent.class)
    public ResponseEntity<List<EventSlide>> getReleasedEvent(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        return ResponseEntity.ok().body(eventService.getEventsReleased(user.getuId()));
    }

    @RequestMapping(value = "${api.user.getDetail}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserDetail(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        return ResponseEntity.ok().body(userService.getByEmail(user.getEmail()));
    }
}
