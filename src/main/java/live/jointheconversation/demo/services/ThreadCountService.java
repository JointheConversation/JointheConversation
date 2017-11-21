package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.ThreadCount;
import live.jointheconversation.demo.repositories.ThreadRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

@Service
public class ThreadCountService {
    private final ThreadRepository threadDao;

    public ThreadCountService(ThreadRepository threadDao){
        this.threadDao=threadDao;
    }
        public void compare(List<ThreadCount> threadCounts) {
        //This code sorts through the list based on the thread count and orders them from high count to low count
            //will need to refactor later in the event of a tie.
        threadCounts.sort(Comparator.comparingLong(ThreadCount::getCount).reversed());
        //THis for each loop checks to make sure hta the sort worked correctly and that the information is beind dispalyed from high thread count to low
            for (ThreadCount threadcount:threadCounts
                 ) {
                System.out.println("The title is "
                        + threadcount.getThreadTitle());
                System.out.println("The thread count is "+threadcount.getCount());
                //This is there We will call up on the ThreadWInner model in order to set the winner into that table.

            }

        }

    }

