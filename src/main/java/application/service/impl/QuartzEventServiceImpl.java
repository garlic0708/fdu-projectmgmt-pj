package application.service.impl;

import application.config.task.EventJob;
import application.entity.Event;
import application.service.QuartzEventService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
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

            Date cancelDate = new Date(event.getStarttime().getTime() + 1000L); // 60*60*1000L
            Date startDate = event.getStarttime();
            Date endDate = event.getEndtime();

            JobBuilder jobBuilder = JobBuilder.newJob(EventJob.class).
                    withIdentity(event.geteId() + "", JOB_GROUP);

            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().
                    withIdentity(event.geteId()+"", TRIGGER_GROUP);

            JobDetail autoCancelJob = jobBuilder.usingJobData(autoCancelJobDataMap).build();
            Trigger autoCancelTrigger = triggerBuilder.startAt(cancelDate).build();
            scheduler.scheduleJob(autoCancelJob, autoCancelTrigger);

            JobDetail markAsStartedJob = jobBuilder.usingJobData(markAsStartedJobDataMap).build();
            Trigger markAsStartedTrigger = triggerBuilder.startAt(startDate).build();
            scheduler.scheduleJob(markAsStartedJob, markAsStartedTrigger);

            JobDetail markAsEndedJob = jobBuilder.usingJobData(markAsEndedJobDataMap).build();
            Trigger markAsEndedTrigger = triggerBuilder.startAt(endDate).build();
            scheduler.scheduleJob(markAsEndedJob, markAsEndedTrigger);

        }
       catch (Exception e) {
            e.printStackTrace();
       }
    }


}
