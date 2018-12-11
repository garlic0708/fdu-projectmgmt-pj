package application.service.impl;

import application.entity.Event;
import application.entity.JoinEvent;
import application.entity.User;
import application.entity.forms.ResultMessage;
import application.entity.forms.UserCheckIn;
import application.exception.JoinEventException;
import application.exception.UpdateUserImgException;
import application.repository.EventRepository;
import application.repository.JoinEventRepository;
import application.repository.UserRepository;
import application.service.FileService;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JoinEventRepository joinEventRepository;
    private EventRepository eventRepository;
    private FileService fileService;

    @Value("${imagePathTitle}")
    private String imagePathTitle;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           JoinEventRepository joinEventRepository,
                           EventRepository eventRepository,
                           FileService fileService) {
        this.userRepository = userRepository;
        this.joinEventRepository = joinEventRepository;
        this.eventRepository = eventRepository;
        this.fileService = fileService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.allStudents();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void joinEvent(int uid, int eid) throws JoinEventException {
        Event event = eventRepository.findByEId(eid);
        User user = userRepository.findByUId(uid);
        if (user == null)
            throw new JoinEventException("No such user");
        if (event == null)
            throw new JoinEventException("No such event");
        if (!event.getEventState().equals("notStarted"))
            throw new JoinEventException("You can not join the event at now");
        if (joinEventRepository.findByUIdAndEId(uid, eid) != null)
            throw new JoinEventException("You have joined the event");
        if (joinEventRepository.getParticipantsByEId(eid).size() >= event.getUpperLimit())
            throw new JoinEventException("Full");
        if (event.getLimited() && event.getCreditLimit() > user.getCredit())
            throw new JoinEventException("You don't have enough credit");
        JoinEvent joinEvent = new JoinEvent();
        joinEvent.setuId(uid);
        joinEvent.seteId(eid);
        joinEvent.setJeState(JoinEvent.PARTICIPATED);
        joinEventRepository.save(joinEvent);
    }

    @Override
    public void quitEvent(int uid, int eid) {
        JoinEvent joinEvent = joinEventRepository.findByUIdAndEId(uid, eid);
        if (joinEvent != null)
            joinEventRepository.delete(joinEvent);
    }

    @Override
    public List<UserCheckIn> getUserCheckIn(int eid) {
        List<JoinEvent> joinEvents = joinEventRepository.findByEId(eid);
        List<UserCheckIn> userCheckIns = new LinkedList<>();
        for (int i = 0; i < joinEvents.size(); i++) {
            JoinEvent joinEvent = joinEvents.get(i);
            int uid = joinEvent.getuId();
            String jeState = joinEvent.getJeState();
            UserCheckIn userCheckIn = new UserCheckIn();
            if (jeState.equals("participated"))
                userCheckIn.setType(false);
            else if (jeState.equals("check"))
                userCheckIn.setType(true);
            else if (jeState.equals("initiator"))
                continue;
            userCheckIn.setUid(uid);
            User user = userRepository.findByUId(uid);
            userCheckIn.setName(user.getNickname());
            userCheckIns.add(userCheckIn);
        }

        return userCheckIns;
    }

    @Override
    public void checkIn(int uid, int eid) {
        Event event = eventRepository.findByEId(eid);
        if (event.getEventState().equals(Event.STARTED)) {
            JoinEvent joinEvent = joinEventRepository.findByUIdAndEId(uid, eid);
            joinEvent.setJeState(JoinEvent.CHECK);
            joinEventRepository.save(joinEvent);
        }

    }

    @Override
    public void checkOut(int uid, int eid) {
        Event event = eventRepository.findByEId(eid);
        if (event.getEventState().equals(Event.STARTED)) {
            JoinEvent joinEvent = joinEventRepository.findByUIdAndEId(uid, eid);
            joinEvent.setJeState(JoinEvent.PARTICIPATED);
            joinEventRepository.save(joinEvent);
        }
    }

    @Override
    public void updateName(int uid, String newName) {
        User user = userRepository.findByUId(uid);
        user.setNickname(newName);
        userRepository.save(user);
    }

    @Override
    public void updateImg(User user, MultipartFile img) throws UpdateUserImgException {
        String fileContentType = img.getContentType();
        if (fileContentType == null || !fileContentType.startsWith("image/"))
            throw new UpdateUserImgException("Img required");

        //得到文件路径和文件名
        String fileName = img.getOriginalFilename();
        String filePath = String.format("%s/%s/", imagePathTitle,
                Calendar.getInstance().getTime()).replace(":", "-");

        try {
            fileService.uploadFile(img.getBytes(), filePath, fileName);
            user.setImage(filePath+fileName);
            userRepository.save(user);
        }
        catch (IOException e) {
            e.printStackTrace();
           throw new UpdateUserImgException("Update img failed");
        }
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
