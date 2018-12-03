package application.service.impl;

import application.entity.*;
import application.entity.forms.AddEventForm;
import application.entity.forms.EventDetail;
import application.entity.forms.EventSlide;
import application.exception.AddEventException;
import application.repository.AddressRepository;
import application.repository.EventRepository;
import application.repository.JoinEventRepository;
import application.repository.MessageRepository;
import application.service.EventService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static application.service.impl.QuartzEventServiceImpl.JOB_GROUP;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private JoinEventRepository joinEventRepository;
    private MessageRepository messageRepository;
    private AddressRepository addressRepository;
    private Scheduler scheduler;

    @Value("${checkTime}")
    private long checkTime;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            JoinEventRepository joinEventRepository,
                            Scheduler scheduler,
                            MessageRepository messageRepository,
                            AddressRepository addressRepository){
        this.eventRepository = eventRepository;
        this.joinEventRepository = joinEventRepository;
        this.scheduler = scheduler;
        this.messageRepository = messageRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public EventDetail getEventDetailById(int eid) {
        return null;
    }


    /**
     * 拿一个到三个数据， 拿前三个
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
     * 拿前十个
     *
     * @return
     */
    @Override
    public List<EventSlide> getHomeFlow(){
        return null;
    }

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
                    message.setContent("对不起，由于您参加的这个活动在规定时间内没有达到人数最低要求，" +
                            "系统已自动取消该活动，给您生活娱乐带来了很大的不便，请您谅解！");
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
    }

    @Override
    public Event addEvent(AddEventForm form, int uid) throws AddEventException{
        Address address = new Address();
        address.setAddressname(form.getAddressName());
        address.setPositionX(form.getAddressPx());
        address.setPositionY(form.getAddressPy());
        address = addressRepository.save(address);

        Date start = form.getStarttime();
        Date end = form.getEndtime();
        Date now = new Date();

        if (now.getTime() + checkTime > start.getTime() || start.getTime() > end.getTime())
            throw new AddEventException("StartTime or endTime is wrong");

        else {
            Event event = new Event();
            event.setEventName(form.getEventname());
            event.setContent(form.getContent());
            event.setStartTime(form.getStarttime());
            event.setEndTime(form.getEndtime());
            event.setAddress(address.getAddrId());
            event.setInitiator(uid);
            event.setEventState("notStarted");
            if (form.getUpperLimit() == null || form.getLowerLimit() == null) {
                event.setLimited(false);
            }
            else {
                event.setLimited(true);
                event.setLowerLimit(form.getLowerLimit());
                event.setUpperLimit(form.getUpperLimit());
                event.setCreditLimit(form.getCredictLimit());
            }
            event.setImage(form.getImage());

            event = eventRepository.save(event);

            // 为创建者添加参与情况
            JoinEvent joinEvent = new JoinEvent();
            joinEvent.seteId(event.geteId());
            joinEvent.setuId(uid);
            joinEvent.setJeState("initiator");
            joinEventRepository.save(joinEvent);

            return event;
        }
    }
}
