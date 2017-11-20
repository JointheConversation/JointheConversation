package live.jointheconversation.demo.scheduledTasks;

import live.jointheconversation.demo.services.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledThread {
    private static final Logger log = LoggerFactory.getLogger(ScheduledThread.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Thread thread;
    private ThreadService threadService;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));

    }

    @Scheduled(fixedRate = 10000)
    public void updateThreads(Thread thread, ThreadService threadService){
        this.thread=thread;
        this.threadService=threadService;

        for(threadService.findAll())
        if(thread.get){

        }


    }
}
