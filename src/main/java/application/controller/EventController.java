package application.controller;

import application.component.JwtTokenUtil;
import application.entity.forms.*;
import application.entity.Event;
import application.entity.User;
import application.exception.AddEventException;
import application.exception.CancelEventException;
import application.service.EventService;
import application.service.FileService;
import application.service.QuartzEventService;
import application.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
@Controller
public class EventController {
    private EventService eventService;
    private QuartzEventService quartzEventService;
    private FileService fileService;
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${imagePathTitle}")
    private String imagePathTitle;

    @Autowired
    public EventController(EventService eventService,
                           QuartzEventService quartzEventService,
                           JwtTokenUtil jwtTokenUtil,
                           UserService userService,
                           FileService fileService) {
        this.eventService = eventService;
        this.quartzEventService = quartzEventService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.fileService = fileService;
    }

    @RequestMapping(value = "${api.event.add}", method = RequestMethod.POST)
    public ResponseEntity<?> addEvent(@RequestParam("file") MultipartFile file,
                                      @RequestParam("addEventForm") String strAddEventForm) {

        User user = jwtTokenUtil.getCurrentUser();

        String fileContentType = file.getContentType();
        if (fileContentType == null || !fileContentType.startsWith("image/"))
            return ResponseEntity.status(400).body(new ResultMessage("Img required"));

        //得到文件路径和文件名
        String fileName = file.getOriginalFilename();
        String filePath = String.format("%s/%s/", imagePathTitle,
                Calendar.getInstance().getTime()).replace(":", "-");

        try {
            if (user != null) {
                fileService.uploadFile(file.getBytes(), filePath, fileName);
                AddEventForm addEventForm = new ObjectMapper().readValue(strAddEventForm, AddEventForm.class);
                addEventForm.setImage(filePath+fileName);
                Event event = eventService.addEvent(addEventForm, user.getuId());
                quartzEventService.addEventJob(event);
                return ResponseEntity.ok(new ResultMessage(event.geteId()+""));
            }
            else {
                return ResponseEntity.status(403).body(new ResultMessage("Add event failed, no such user"));
            }
        }
        catch (AddEventException e) {
            return ResponseEntity.status(400).body(new ResultMessage(e.getMessage()));
        }
        catch (IOException e) {
            return ResponseEntity.status(500).body(new ResultMessage("Add event failed"));
        }
    }

    @RequestMapping(value = "${api.event.cancel}/{eid}", method = RequestMethod.PUT)
    public ResponseEntity<?> cancelEvent(@PathVariable("eid") int eid) {

        User user = jwtTokenUtil.getCurrentUser();

        try {
            eventService.cancelEvent(user.getuId(), eid);
            return ResponseEntity.ok().body(new ResultMessage("Cancle event success"));
        }catch (CancelEventException e) {
            return ResponseEntity.status(400).body(new ResultMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "${api.event.detail}/{eid}", method = RequestMethod.GET)
    public ResponseEntity<?> getEventDetail(@PathVariable("eid") int eid) {

        User user = jwtTokenUtil.getCurrentUser();
        return ResponseEntity.ok().body(eventService.getEventDetailById(eid, user.getuId()));
    }

    @RequestMapping(value = "${api.event.home-slides}", method = RequestMethod.GET)
    public ResponseEntity<?> getHomeSlides() {
        return ResponseEntity.ok().body(eventService.getHomeSlides());
    }

    @RequestMapping(value = "${api.event.home-flow}", method = RequestMethod.GET)
    public ResponseEntity<?> getHomeFlow() {
        return ResponseEntity.ok().body(eventService.getHomeFlow());
    }

    @RequestMapping(value = "${api.event.checkin}/{eid}", method = RequestMethod.GET)
    public ResponseEntity<?> getCheckIn(@PathVariable("eid") int eid) {
        return ResponseEntity.ok().body(userService.getUserCheckIn(eid));
    }

    @RequestMapping(value = "${api.event.nearby}", method = RequestMethod.GET)
    @JsonView(View.NearByEvent.class)
    public ResponseEntity<List<EventDetail>> getNearbyEvents(@RequestParam("nex") double nex, @RequestParam("ney") double ney,
                                                             @RequestParam("swx") double swx, @RequestParam("swy") double swy) {
        return ResponseEntity.ok().body(eventService.getNearbyEvents(nex, ney, swx, swy));
    }

    @RequestMapping(value = "${api.event.search}", method = RequestMethod.GET)
    public ResponseEntity<List<EventSlide>> searchEvents(@RequestParam("search") String search,
                                                         @RequestParam("page") int page) {
        return ResponseEntity.ok(eventService.searchEvents(search, page));
    }
}
