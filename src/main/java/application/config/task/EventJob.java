package application.config.task;

import application.service.EventService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/12/3.
 */


@Configuration
@Component
@EnableScheduling
public class EventJob extends QuartzJobBean {
    private static Logger logger = LoggerFactory.getLogger(EventJob.class);
    private int eventId;
    private EventService eventService;

    @Autowired
    public EventJob(EventService eventService) {
        this.eventService = eventService;
    }


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            this.eventId = (int)jobDataMap.get("eid");
            String type = jobDataMap.getString("type");
            logger.info("任务执行: " + eventId + "  "+ type);
            switch (type) {
                case "autoCancel":
                    eventService.autoCancel(eventId);
                    break;
                case "markAsStarted":
                    eventService.markAsStarted(eventId);
                    break;
                case "markAsEnded":
                    eventService.markAsEnded(eventId);
                    break;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
