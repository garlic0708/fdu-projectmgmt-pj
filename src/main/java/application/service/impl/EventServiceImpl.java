package application.service.impl;

import application.entity.*;
import application.entity.forms.AddEventForm;
import application.entity.forms.EventDetail;
import application.entity.forms.EventSlide;
import application.exception.AddEventException;
import application.repository.*;
import application.service.EventService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
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

    @Value("${messages.cancelEventMessage}")
    private String cancelEventMessage;

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
        for (int i=0;i<3;i++) {
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
        for (int i=0;i<10;i++) {
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
                event.setEventState("canceled");

                for (Integer uid: list) {
                    Message message = new Message();
                    message.setSender(0);
                    message.setReceiver(uid);
                    message.setContent(cancelEventMessage);
                    message.setMessagestate("Unread");
                    messageRepository.save(message);
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
        event.setEventState("started");
        eventRepository.save(event);
    }

    @Override
    public void markAsEnded(int eid) {
        Event event = eventRepository.findByEId(eid);
        event.setEventState("ended");
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
            event.setEventState("notStarted");

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
    public Event getById(int eid) {
        return eventRepository.findByEId(eid);
    }

    @Override
    public List<Event> getEventsJoined(int uid) {
        //TODO
        return null;
    }

    @Override
    public List<Event> getEventsReleased(int uid) {
        //TODO
        return null;
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
}
