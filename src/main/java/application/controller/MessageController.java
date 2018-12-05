package application.controller;

import application.component.JwtTokenUtil;
import application.entity.User;
import application.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        String token = httpServletRequest.getHeader(tokenHeader).substring(tokenHead.length());
        User user = jwtTokenUtil.getUserByToken(token);

        return ResponseEntity.ok().body(messageService.getMessagesByUid(user.getuId()));
    }
}
