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
        threadCounts.sort(Comparator.comparingLong(ThreadCount::getCount).reversed());
            for (ThreadCount threadcount:threadCounts
                 ) {
                System.out.println("The title is "
                        + threadcount.getThreadTitle());
                System.out.println("The thread count is "+threadcount.getCount());

            }

        }

    }

