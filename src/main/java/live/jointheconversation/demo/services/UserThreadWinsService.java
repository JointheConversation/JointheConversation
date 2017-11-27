package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.repositories.ThreadWinnerRepository;
import live.jointheconversation.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserThreadWinsService {
    private ThreadWinnerRepository threadWinnerDao;
    private UserRepository userDao;
    private ThreadRepository threadDao;
    private UserOwnerService userOwnerService;

    public UserThreadWinsService(ThreadWinnerRepository threadWinnerDao, UserRepository userDao, ThreadRepository threadDao, UserOwnerService userOwnerService){
        this.threadDao=threadDao;
        this.threadWinnerDao=threadWinnerDao;
        this.userDao=userDao;
        this.userOwnerService=userOwnerService;
    }

    public List<Thread> ShowAllAwards(User user){
        List<Thread> threadsAwards=threadDao.findAllWinnerThreadsOfUser(user);
        return threadsAwards;
    }
}
