package live.jointheconversation.demo.scheduledTasks;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.ThreadCount;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.services.ThreadCountService;
import live.jointheconversation.demo.services.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledThread {
    private static final Logger log = LoggerFactory.getLogger(ScheduledThread.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private ThreadService threadService;
    private ThreadRepository threadDao;
    private ThreadCountService threadCountService;



    @Autowired
    public ScheduledThread(ThreadService threadService, ThreadRepository threadDao, ThreadCountService threadCountService){
        this.threadService=threadService;
        this.threadDao=threadDao;
        this.threadCountService=threadCountService;
    }

//    @Scheduled(fixedRate = 30000)
//    public void reportCurrentTime() {
////        log.info("30 seconds have passed, the time is now {}", dateFormat.format(new Date()));
//
//    }
    @Scheduled(fixedRate = 15000) //15 seconds
    public void countThreadPosts(){
        //Post post = (Post)
        List<ThreadCount> threadCounts = threadDao.countPostsInThreads();
        threadCountService.compare(threadCounts);
        }
//
//
//
//    }
//
//    @Scheduled(fixedRate = 60000) // 1 minute
//    public void updateThreads(){
////        this.threadService=threadService;
//        for (Thread thread:threadService.findAll()) {
//
//            Date endDate = new Date();
//            Date startDate=thread.getDate();
//            long duration  = endDate.getTime() - startDate.getTime();
//
//            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
//            //Finds time difference in seconds
//            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
//            // Finds time difference in minutes
//            long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
//            // Finds time difference in hours
//
//            if(diffInSeconds>=30) {
//                if (thread.getActiveStatus()) {
//                    thread.setActiveStatus(false);
//                    threadService.save(thread);
//                }
//            }
//        }
//    }
}
