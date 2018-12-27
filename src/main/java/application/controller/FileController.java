package application.controller;

import application.entity.forms.ResultMessage;
import application.service.EventService;
import application.service.FileService;
import application.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.NoSuchFileException;

/**
 * Creator: DreamBoy
 * Date: 2018/12/4.
 */
@Controller
public class FileController {
    private EventService eventService;
    private UserService userService;
    private FileService fileService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Value("${download.resource}")
    private String downloadResource;

    @Value("${download.filename}")
    private String downloadFilename;

    @Autowired
    public FileController(EventService eventService,
                          FileService fileService,
                          UserService userService) {
        this.eventService = eventService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @RequestMapping(value = "${api.image.get.event}/{eid}", method = RequestMethod.GET)
    public ResponseEntity<?> getEventImage(@PathVariable("eid") int eid) {
        String imagePath = eventService.getById(eid).getImage();
        try {
            return getImgByPath(fileService.getImage(imagePath));
        } catch (NoSuchFileException e) {
            return getImgByPath(new ClassPathResource("static/no-image.jpg"));
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResultMessage("Failed to load image"));
        }
    }

    @RequestMapping(value = "${api.image.get.user}/{uid}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserImage(@PathVariable("uid") int uid) {
        String imagePath = userService.getByUid(uid).getImage();
        Resource binaryImage;
        try {
            if (imagePath == null || imagePath.equals(""))
                binaryImage = new ClassPathResource("static/defaultUser.jpg");
            else binaryImage = fileService.getImage(imagePath);
        } catch (NoSuchFileException e) {
            binaryImage = new ClassPathResource("static/defaultUser.jpg");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResultMessage("Failed to load image"));
        }
        return getImgByPath(binaryImage);
    }

    @GetMapping(value = "${download.url}")
    public ResponseEntity<?> downloadFile() {
        Resource resource = new ClassPathResource(downloadResource);
        return ResponseEntity
                .ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s",
                        downloadFilename))
                .body(resource);
    }

    private ResponseEntity<?> getImgByPath(Resource image) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }
}
