package application.controller;

import application.component.JwtTokenUtil;
import application.entity.User;
import application.entity.forms.ResultMessage;
import application.exception.ReadMessageException;
import application.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Creator: DreamBoy
 * Date: 2018/12/5.
 */
@Controller
public class MessageController {
    private MessageService messageService;
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public MessageController(MessageService messageService,
                             JwtTokenUtil jwtTokenUtil) {
        this.messageService = messageService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping(value = "${api.notify.list}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserDetail(HttpServletRequest httpServletRequest) {

        User user = jwtTokenUtil.getCurrentUser();

        return ResponseEntity.ok().body(messageService.getMessagesByUid(user.getuId()));
    }

    @RequestMapping(value = "${api.notify.read}/{mid}", method = RequestMethod.PUT)
    public ResponseEntity<?> readMessage(@PathVariable("mid") String mid, HttpServletRequest httpServletRequest) {

        User user = jwtTokenUtil.getCurrentUser();

        try {
            messageService.readMessage(user.getuId(), Integer.parseInt(mid));
            return ResponseEntity.ok().body(new ResultMessage("Read message success"));
        }
        catch (ReadMessageException e) {
            return ResponseEntity.status(400).body(new ResultMessage(e.getMessage()));
        }
    }
}
