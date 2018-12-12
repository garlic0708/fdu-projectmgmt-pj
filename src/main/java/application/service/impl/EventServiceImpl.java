package application.service.impl;

import application.entity.*;
import application.entity.forms.AddEventForm;
import application.entity.forms.EventDetail;
import application.entity.forms.EventSlide;
import application.exception.AddEventException;
import application.exception.CancelEventException;
import application.repository.*;
import application.service.EventService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static application.service.impl.QuartzEventServiceImpl.JOB_GROUP;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private JoinEventRepository joinEventRepository;
    private MessageRepository messageRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private EventTagRepository eventTagRepository;
    private TagRepository tagRepository;
    private StartEventRepository startEventRepository;
    private Scheduler scheduler;

    @Value("${checkTime}")
    private long checkTime;

    @Value("${messages.cancelEvent.time.others}")
    private String cancelEventTimeOthersMessage;
    @Value("${messages.cancelEvent.time.initiator}")
    private String cancelEventTimeInitiatorMessage;
    @Value("${messages.cancelEvent.user.others}")
    private String cancelEventUserOthersMessage;
    @Value("${messages.cancelEvent.user.initiator}")
    private String cancelEventUserInitiatorMessage;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            JoinEventRepository joinEventRepository,
                            Scheduler scheduler,
                            MessageRepository messageRepository,
                            AddressRepository addressRepository,
                            UserRepository userRepository,
                            EventTagRepository eventTagRepository,
                            TagRepository tagRepository,
                            StartEventRepository startEventRepository){
        this.eventRepository = eventRepository;
        this.joinEventRepository = joinEventRepository;
        this.scheduler = scheduler;
        this.messageRepository = messageRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.eventTagRepository = eventTagRepository;
        this.tagRepository = tagRepository;
        this.startEventRepository = startEventRepository;
    }

    @Override
    public Map<String, Object> getEventDetailById(int eid, int uid) {
        Event event=eventRepository.findByEId(eid);
        Integer aId=event.getAddress();
        Address address=addressRepository.findByAddrId(aId);
        EventDetail eventDetail=new EventDetail(eid,event.getEventName(),event.getContent(), event.getStartTime(),
                event.getEndTime(),address,event.getEventState(),event.getLimited(),event.getCreditLimit(),
                event.getUpperLimit(),event.getLowerLimit(),event.getImage()
        );

        List<EventTag> eventTagList = eventTagRepository.findByEId(eid);
        List<Tag> tagList = new LinkedList<>();
        for (int i = 0; i < eventTagList.size(); i++) {
            EventTag eventTag = eventTagList.get(i);
            int tid = eventTag.gettId();
            Tag tag = tagRepository.findByTId(tid);
            tagList.add(tag);
        }
        eventDetail.setTags(tagList);

        Integer initiator = event.getInitiator();
        User user = userRepository.findByUId(initiator);
        Map<String, String> map = new HashMap<>();
        map.put("name", user.getNickname());
        map.put("avatar", user.getImage());
        eventDetail.setInitiator(map);

        List<JoinEvent> joinEventList = joinEventRepository.findByEId(eid);
        eventDetail.setCurrentAttendants(joinEventList.size());

        Map<String, Object> detail = new HashMap<>();
        detail.put("detail", eventDetail);

        JoinEvent joinEvent = joinEventRepository.findByUIdAndEId(uid, eid);
        if (joinEvent == null)
            detail.put("state", JoinEvent.NOTJOINED);
        else
            detail.put("state", joinEvent.getJeState());

        return detail;
    }


    /**
     * 拿一个到三个数据， 拿后三个
     *
     * @return
     */
    @Override
    public List<EventSlide> getHomeSlides(){
        List<Event> events=eventRepository.getEvents(3);
        List<EventSlide> eventSlides=new LinkedList<>();
        for (int i=0;i<events.size();i++) {
            String path=events.get(i).getImage();
            String title=events.get(i).getEventName();
            int id=events.get(i).geteId();
            EventSlide eventSlide = new EventSlide(path,title,id);
            eventSlides.add(eventSlide);
        }
        return eventSlides;
    }

    /**
     * 拿后十个
     *
     * @return
     */
    @Override
    public List<EventSlide> getHomeFlow(){
        List<Event> events=eventRepository.getEvents(10);
        List<EventSlide> eventSlides=new LinkedList<>();
        for (int i=0;i<events.size();i++) {
            String path=events.get(i).getImage();
            String title=events.get(i).getEventName();
            int id=events.get(i).geteId();
            EventSlide eventSlide = new EventSlide(path,title,id);
            eventSlides.add(eventSlide);
        }
        return eventSlides;    }

    @Override
    public List<Integer> getParticipants(int eid) {
        return joinEventRepository.getParticipantsByEId(eid);
    }

    @Override
    public void autoCancel(int eid) {
        try {
            Event event = eventRepository.findByEId(eid);
            List<Integer> list = getParticipants(eid);

            if (event.getLimited() && event.getLowerLimit() > list.size()) {
                event.setEventState(Event.CANCELED);

                for (Integer ouid: list) {
                    if (ouid != eid)
                        sendMessage(0, ouid, cancelEventTimeOthersMessage.replace("%event%", "("+ event.getEventName() + ")"));
                    else
                        sendMessage(0, ouid, cancelEventTimeInitiatorMessage.replace("%event%", "("+ event.getEventName() + ")"));
                }

                eventRepository.save(event);
                scheduler.deleteJob(JobKey.jobKey(eid + " markAsStarted", JOB_GROUP));
                scheduler.deleteJob(JobKey.jobKey(eid + " markAsEnded", JOB_GROUP));
            }

        }
       catch (Exception e) {
            System.out.println(e.getMessage());
       }
    }

    @Override
    public void markAsStarted(int eid) {
        Event event = eventRepository.findByEId(eid);
        event.setEventState(Event.STARTED);
        eventRepository.save(event);
    }

    @Override
    public void markAsEnded(int eid) {
        Event event = eventRepository.findByEId(eid);
        event.setEventState(Event.ENDED);
        eventRepository.save(event);

        //调整信用度
        updateCredit(eid);
    }

    @Override
    public Event addEvent(AddEventForm form, int uid) throws AddEventException{
        String poiId = form.getPoiId();
        Address address;
        if (poiId == null || ((address = addressRepository.findByPoiId(poiId)) == null)) {
            address = new Address();
            address.setPoiId(form.getPoiId());
            address.setAddressName(form.getAddressName());
            address.setAddressPosition(form.getAddressLocation());
            address.setPositionX(form.getAddressPx());
            address.setPositionY(form.getAddressPy());
            address = addressRepository.save(address);
        }


        Date start = form.getStartTime();
        Date end = form.getEndTime();
        Date now = new Date();

        if (now.getTime() + checkTime > start.getTime() || start.getTime() > end.getTime())
            throw new AddEventException("StartTime or endTime is wrong");

        else {
            Event event = new Event();
            event.setEventName(form.getEventName());
            event.setContent(form.getContent());
            event.setStartTime(form.getStartTime());
            event.setEndTime(form.getEndTime());
            event.setAddress(address.getAddrId());
            event.setInitiator(uid);
            event.setEventState(Event.NOTSTARTED);

            Integer ul = form.getUpperLimit();
            Integer ll = form.getLowerLimit();

            if (ul == null && ll == null) { // 上限和下限都没有时，才设置limited为false
                event.setLimited(false);
            }
            else  {
                event.setLimited(true);
                if (ul != null)
                    event.setUpperLimit(ul);
                if (ll != null)
                    event.setLowerLimit(ll);
            }
            if (form.getCreditLimit() == null)
                event.setCreditLimit(0);
            else
                event.setCreditLimit(form.getCreditLimit());

            event.setImage(form.getImage());

            event = eventRepository.save(event);

            // 为创建者添加参与情况
            JoinEvent joinEvent = new JoinEvent();
            joinEvent.seteId(event.geteId());
            joinEvent.setuId(uid);
            joinEvent.setJeState(JoinEvent.INITIATOR);
            joinEventRepository.save(joinEvent);

            // 为创建者添加创建情况
            StartEvent startEvent = new StartEvent();
            joinEvent.seteId(event.geteId());
            joinEvent.setuId(uid);
            startEventRepository.save(startEvent);

            // 添加event-tag
            List<Integer> tags = form.getTags();
            for (int i: tags) {
                if (tagRepository.findByTId(i) != null) {
                    EventTag eventTag = new EventTag();
                    eventTag.seteId(event.geteId());
                    eventTag.settId(i);
                    eventTagRepository.save(eventTag);
                }
            }
            return event;
        }
    }

    @Override
    public void cancelEvent(int uid, int eid) throws CancelEventException {
        Event event = eventRepository.findByEId(eid);
        if (event == null)
            throw new CancelEventException("No such Event");
        if (event.getInitiator() != uid)
            throw new CancelEventException("Permission denied");
        event.setEventState(Event.CANCELED);

        List<Integer> list = getParticipants(eid);

        for (Integer ouid: list) {
            if (ouid != eid)
                sendMessage(0, ouid, cancelEventUserOthersMessage.replace("%event%", "("+ event.getEventName() + ")"));
        }
        try {
            //TODO
            scheduler.deleteJob(JobKey.jobKey(eid + " markAsStarted", JOB_GROUP));
            scheduler.deleteJob(JobKey.jobKey(eid + " markAsEnded", JOB_GROUP));
        }
        catch (SchedulerException e) {
            e.printStackTrace();
        }
        eventRepository.save(event);
    }

    @Override
    public List<EventDetail> getNearbyEvents(double x1, double y1, double x2, double y2) {
        List<Event> events = eventRepository.getEventsInASquare(x1, y1, x2, y2);
        List<EventDetail> details = new ArrayList<>();
        for (Event event: events) {
            EventDetail detail = new EventDetail();
            detail.seteId(event.geteId());
            detail.setEventName(event.getEventName());
            detail.setAddress(addressRepository.findByAddrId(event.getAddress()));
            details.add(detail);
        }
        return details;
    }

    @Override
    public Event getById(int eid) {
        return eventRepository.findByEId(eid);
    }

    @Override
    public List<EventSlide> getEventsJoined(int uid) {
        List<JoinEvent> joinEvents = joinEventRepository.findByUId(uid);
        List<EventSlide> slides = new ArrayList<>();
        for (JoinEvent joinEvent: joinEvents) {
            if (!joinEvent.getJeState().equals(JoinEvent.INITIATOR)) {
                Event event = eventRepository.findByEId(joinEvent.geteId());
                EventSlide slide = new EventSlide();
                slide.setId(event.geteId());
                slide.setTitle(event.getEventName());
                slide.setStatus(event.getEventState());
                slides.add(slide);
            }
        }
        return slides;
    }

    @Override
    public List<EventSlide> getEventsReleased(int uid) {
        List<Event> events = eventRepository.findByInitiator(uid);
        List<EventSlide> slides = new ArrayList<>();
        for (Event event: events) {
            EventSlide slide = new EventSlide();
            slide.setId(event.geteId());
            slide.setTitle(event.getEventName());
            slide.setStatus(event.getEventState());
            slides.add(slide);

        }
        return slides;
    }

    private void updateCredit(int eid) {
        List<JoinEvent> joinEvents = joinEventRepository.getParticipantsByEId2(eid);
        User user;
        for (JoinEvent joinEvent: joinEvents) {
            if (Objects.equals(joinEvent.getJeState(), JoinEvent.PARTICIPATED)) { //只是参加，没有签到
                user = userRepository.findByUId(joinEvent.getuId());
                int credit = user.getCredit();
                credit = credit-1 >= 0 ? credit-1 : 0;
                user.setCredit(credit);
                userRepository.save(user);
            }
            else if (Objects.equals(joinEvent.getJeState(), JoinEvent.CHECK)) {
                user = userRepository.findByUId(joinEvent.getuId());
                int credit = user.getCredit();
                credit = credit+1 >= 100 ? credit+1 : 100;
                user.setCredit(credit);
                userRepository.save(user);
            }
        }
    }

    private void sendMessage(int sender, int receiver, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setMessageState("Unread");
        messageRepository.save(message);
    }
}
