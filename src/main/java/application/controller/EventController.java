package application.controller;

import application.component.JwtTokenUtil;
import application.entity.forms.AddEventForm;
import application.entity.Event;
import application.entity.forms.ResultMessage;
import application.entity.User;
import application.exception.AddEventException;
import application.service.EventService;
import application.service.QuartzEventService;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
@Controller
public class EventController {
    private EventService eventService;
    private QuartzEventService quartzEventService;
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public EventController(EventService eventService,
                           QuartzEventService quartzEventService,
                           JwtTokenUtil jwtTokenUtil,
                           UserService userService) {
        this.eventService = eventService;
        this.quartzEventService = quartzEventService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @RequestMapping(value = "${api.event.add}", method = RequestMethod.POST)
    public ResponseEntity<?> addEvent(HttpServletRequest httpServletRequest, @RequestBody AddEventForm addEventForm) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);
        try {
            if (user != null) {
                Event event = eventService.addEvent(addEventForm, user.getuId());
                quartzEventService.addEventJob(event);
                return ResponseEntity.ok(new ResultMessage("Add event success"));
            }
            else {
                return ResponseEntity.status(424).body(new ResultMessage("Add event failed, no such user"));
            }
        }
        catch (AddEventException e) {
            return ResponseEntity.status(424).body(new ResultMessage(e.getMessage()));
        }
    }
}
