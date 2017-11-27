package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.ThreadWinner;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.repositories.ThreadWinnerRepository;
import live.jointheconversation.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class WinningThreadInfoService {
    private ThreadWinnerRepository threadWinnerDao;
    private ThreadRepository threadDao;
    private UserRepository userDao;

    public WinningThreadInfoService(ThreadWinnerRepository threadWinnerDao, ThreadRepository threadDao, UserRepository userDao){
        this.threadDao=threadDao;
        this.threadWinnerDao=threadWinnerDao;
        this.userDao=userDao;
    }
    public Thread RetrieveThreadWinnerInfo(){
        Iterable<ThreadWinner> threadWinners=threadWinnerDao.findAll();
        for (ThreadWinner threadWinner:threadWinners) {
            if(threadWinner.isActiveStatus()){
                Thread thread=threadDao.findOne(threadWinner.getId());
                System.out.println("Winning thread title "+thread.getTitle());
                return thread;
            }
        }
        return null;
    }
}
