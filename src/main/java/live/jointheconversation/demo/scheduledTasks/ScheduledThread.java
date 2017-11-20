package live.jointheconversation.demo.scheduledTasks;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.services.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledThread {
    private static final Logger log = LoggerFactory.getLogger(ScheduledThread.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private ThreadService threadService;


    @Autowired
    public ScheduledThread(ThreadService threadService){
        this.threadService=threadService;
    }

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
//        log.info("30 seconds have passed, the time is now {}", dateFormat.format(new Date()));

    }

    @Scheduled(fixedRate = 30000) // 30 seconds
    public void updateThreads(){
//        this.threadService=threadService;


        for (Thread thread:threadService.findAll()) {
            Date endDate = new Date();
            Date startDate=thread.getDate();
            long duration  = endDate.getTime() - startDate.getTime();

            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
            //Finds time difference in seconds
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            // Finds time difference in minutes
            long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
            // Finds time difference in hours

            if(diffInSeconds>=30) {
                System.out.println("30 seconds have passed");
                if (thread.getActiveStatus()) {
                    thread.setActiveStatus(false);
                    threadService.save(thread);
                }
            }
        }
    }
}
