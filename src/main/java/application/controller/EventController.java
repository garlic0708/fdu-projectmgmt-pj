package application.controller;

import application.component.JwtTokenUtil;
import application.entity.forms.AddEventForm;
import application.entity.Event;
import application.entity.forms.ResultMessage;
import application.entity.User;
import application.exception.AddEventException;
import application.service.EventService;
import application.service.FileService;
import application.service.QuartzEventService;
import application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
@Controller
public class EventController {
    private EventService eventService;
    private QuartzEventService quartzEventService;
    private FileService fileService;
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public EventController(EventService eventService,
                           QuartzEventService quartzEventService,
                           JwtTokenUtil jwtTokenUtil,
                           FileService fileService) {
        this.eventService = eventService;
        this.quartzEventService = quartzEventService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.fileService = fileService;
    }

    @RequestMapping(value = "${api.event.add}", method = RequestMethod.POST)
    public ResponseEntity<?> addEvent(HttpServletRequest httpServletRequest,
                                      @RequestParam("file") MultipartFile file,
                                      @RequestParam("addEventForm") String strAddEventForm) {
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        String fileContentType = file.getContentType();
        if (fileContentType == null || !fileContentType.startsWith("image/"))
            return ResponseEntity.status(425).body(new ResultMessage("Img required"));

        //得到文件路径和文件名
        String fileName = file.getOriginalFilename();
        String filePath = httpServletRequest.getSession().getServletContext().getRealPath(
                String.format("/%s/", Calendar.getInstance().getTime()).replace(":", "-"));

        try {
            if (user != null) {
                fileService.uploadFile(file.getBytes(), filePath, fileName);
                AddEventForm addEventForm = new ObjectMapper().readValue(strAddEventForm, AddEventForm.class);
                addEventForm.setImage(filePath+fileName);
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
        catch (IOException e) {
            return ResponseEntity.status(425).body(new ResultMessage("Add event failed"));
        }
    }


}
