package application.service.impl;

import application.config.task.EventJob;
import application.entity.Event;
import application.service.QuartzEventService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Creator: DreamBoy
 * Date: 2018/12/3.
 */
@Service
public class QuartzEventServiceImpl implements QuartzEventService {
    static final String JOB_GROUP = "event_job_group";
    private static final String TRIGGER_GROUP = "event_trigger_group";

    @Value("${checkTime}")
    private long checkTime;

    private Scheduler scheduler;

    @Autowired
    public QuartzEventServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void addEventJob(Event event) {
        try {
            JobDataMap autoCancelJobDataMap = new JobDataMap();
            autoCancelJobDataMap.put("eid", event.geteId());
            autoCancelJobDataMap.put("type", "autoCancel");

            JobDataMap markAsStartedJobDataMap = new JobDataMap();
            markAsStartedJobDataMap.put("eid", event.geteId());
            markAsStartedJobDataMap.put("type", "markAsStarted");

            JobDataMap markAsEndedJobDataMap = new JobDataMap();
            markAsEndedJobDataMap.put("eid", event.geteId());
            markAsEndedJobDataMap.put("type", "markAsEnded");

            Date cancelDate = new Date(event.getStartTime().getTime() - checkTime); // 60*60*1000L
            Date startDate = event.getStartTime();
            Date endDate = event.getEndTime();

            if (event.getLimited()) { // 有人数限制的活动才需要自动取消的功能
                JobDetail autoCancelJob = JobBuilder.newJob(EventJob.class).
                        withIdentity(event.geteId() + " autoCancel", JOB_GROUP).usingJobData(autoCancelJobDataMap).build();
                Trigger autoCancelTrigger = TriggerBuilder.newTrigger().
                        withIdentity(event.geteId()+" autoCancel", TRIGGER_GROUP).startAt(cancelDate).build();
                scheduler.scheduleJob(autoCancelJob, autoCancelTrigger);
            }

            JobDetail markAsStartedJob = JobBuilder.newJob(EventJob.class).
                    withIdentity(event.geteId() + " markAsStarted", JOB_GROUP).usingJobData(markAsStartedJobDataMap).build();
            Trigger markAsStartedTrigger = TriggerBuilder.newTrigger().
                    withIdentity(event.geteId()+" markAsStarted", TRIGGER_GROUP).startAt(startDate).build();
            scheduler.scheduleJob(markAsStartedJob, markAsStartedTrigger);

            JobDetail markAsEndedJob = JobBuilder.newJob(EventJob.class).
                    withIdentity(event.geteId() + " markAsEnded", JOB_GROUP).usingJobData(markAsEndedJobDataMap).build();
            Trigger markAsEndedTrigger = TriggerBuilder.newTrigger().
                    withIdentity(event.geteId()+" markAsEnded", TRIGGER_GROUP).startAt(endDate).build();
            scheduler.scheduleJob(markAsEndedJob, markAsEndedTrigger);
        }
       catch (Exception e) {
            e.printStackTrace();
       }
    }


}
