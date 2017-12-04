package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.ThreadCount;
import live.jointheconversation.demo.models.ThreadWinner;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.repositories.ThreadWinnerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

//This service counts through the threads for the number of posts and then assigns the winner into the thread_winner table.
@Service
public class ThreadCountService {
    private final ThreadRepository threadDao;
    private final ThreadWinnerRepository threadWinnerDao;
    private final ThreadService threadService;


    public ThreadCountService(ThreadRepository threadDao, ThreadWinnerRepository threadWinnerDao, ThreadService threadService){
        this.threadDao=threadDao;
        this.threadWinnerDao=threadWinnerDao;
        this.threadService=threadService;
    }
        public void compare(List<ThreadCount> threadCounts) {
        //This code sorts through the list based on the thread count and orders them from high count to low count
            //will need to refactor later in the event of a tie.
        threadCounts.sort(Comparator.comparingLong(ThreadCount::getCount).reversed());



        //THis for each loop checks to make sure hta the sort worked correctly and that the information is being displayed from high thread count to low
//            int i=0;
//            for (ThreadCount threadcount:threadCounts) {
//                System.out.println("The current number is "+i);
//                if(i==0){
//                    Thread thread=new Thread();
//                    thread.setId(threadcount.getId());
//                    ThreadWinnerRepository threadWinner = new ThreadWinnerRepository();
//                    threadWinner.setThread(thread);
//
//                }
//                i++;

                    ThreadCount threadCount= threadCounts.get(0);
                    ThreadWinner threadWinner = new ThreadWinner();
                    Thread thread=threadDao.findOne(threadCount.getId());
                    threadWinner.setThread(thread);
                    threadWinner.setActiveStatus(true);
                    threadWinnerDao.save(threadWinner);


                System.out.println("The title is "
                        + threadCount.getThreadTitle()+ " with an id of: "+ threadCount.getId());
                System.out.println("The thread count is "+threadCount.getCount());

                // This segment will close out the all active threads since they are no longer able to be viewed or commented on.
            //Turn back on later
//                Iterable<Thread> threadsList=threadService.findAll();
//            for (Thread threadIndexed:threadsList) {
//                threadIndexed.setActiveStatus(false);
//                threadService.save(threadIndexed);
//            }
                //This is there We will call up on the ThreadWInner model in order to set the winner into that table.
            }
            public Thread firstPlaceThread(List<ThreadCount> threadCounts){
                if(threadCounts.isEmpty()){
                    return null;
                }
                threadCounts.sort(Comparator.comparingLong(ThreadCount::getCount).reversed());
                ThreadCount threadCount=threadCounts.get(0); //This is the highest threadcount at the time.
                Thread thread= new Thread();
                thread=threadService.findById(threadCount.getId());
                return thread;
            }


    }

