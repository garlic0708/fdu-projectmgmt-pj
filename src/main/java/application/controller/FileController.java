package application.controller;

import application.entity.forms.ResultMessage;
import application.service.EventService;
import application.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Creator: DreamBoy
 * Date: 2018/12/4.
 */
@Controller
public class FileController {
    private EventService eventService;
    private FileService fileService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public FileController(EventService eventService, FileService fileService) {
        this.eventService = eventService;
        this.fileService = fileService;
    }

    @RequestMapping(value = "${api.image.get}/{eid}", method = RequestMethod.GET)
    public ResponseEntity<?> addEvent(@PathVariable("eid") int eid) {
        String imagePath = eventService.getById(eid).getImage();
        try {
            byte[] image = fileService.getImage(imagePath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
        }
        catch (Exception e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(427).body(new ResultMessage("Failed to load image"));
        }
    }
}
