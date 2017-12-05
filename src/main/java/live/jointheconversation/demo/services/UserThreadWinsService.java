package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.repositories.PostRepository;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.repositories.ThreadWinnerRepository;
import live.jointheconversation.demo.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserThreadWinsService {
    private ThreadWinnerRepository threadWinnerDao;
    private UserRepository userDao;
    private ThreadRepository threadDao;
    private UserOwnerService userOwnerService;
    private WinningThreadInfoService winningThreadInfoService;
    private PostRepository postDao;

    public UserThreadWinsService(ThreadWinnerRepository threadWinnerDao, UserRepository userDao, ThreadRepository threadDao, UserOwnerService userOwnerService, WinningThreadInfoService winningThreadInfoService, PostRepository postDao) {
        this.threadDao = threadDao;
        this.threadWinnerDao = threadWinnerDao;
        this.userDao = userDao;
        this.userOwnerService = userOwnerService;
        this.winningThreadInfoService = winningThreadInfoService;
        this.postDao = postDao;
    }

    public List<Thread> ShowAllThreadWinningAwards(User user) {
        List<Thread> threadsAwards = threadDao.findAllWinnerThreadsOfUser(user);
        return threadsAwards;
    }

//    public List<Thread> showUserCurrentEventsThreadList(User user) {
//        List<Thread> threadsCE = new ArrayList<>();
//        for (Thread thread : user.getThreads()) {
//            if (thread.getCategory().equals("Current-Events")) {
//                threadsCE.add(thread);
//            }
//        }
//        return threadsCE;
//
//    }
//
//    public List<Thread> showUserArtThreadList(User user) {
//        List<Thread> threadsArt = new ArrayList<>();
//        for (Thread thread : user.getThreads()) {
//            if (thread.getCategory().equals("Art")) {
//                threadsArt.add(thread);
//            }
//        }
//        return threadsArt;
//    }
//
//    public List<Thread> showUserHumorThreadList(User user) {
//        List<Thread> threadsHumor = new ArrayList<>();
//        for (Thread thread : user.getThreads()) {
//            if (thread.getCategory().equals("Humor")) {
//                threadsHumor.add(thread);
//            }
//        }
//        return threadsHumor;
//
//    }
//
//    public List<Thread> showUserFoodThreadList(User user) {
//        List<Thread> threadsFood = new ArrayList<>();
//        for (Thread thread : user.getThreads()) {
//            if (thread.getCategory().equals("Food")) {
//                threadsFood.add(thread);
//            }
//        }
//        return threadsFood;
//    }
//
//    public List<Thread> showUserSportsThreadList(User user) {
//        List<Thread> threadsSports = new ArrayList<>();
//        for (Thread thread : user.getThreads()) {
//            if (thread.getCategory().equals("Sports")) {
//                threadsSports.add(thread);
//            }
//        }
//        return threadsSports;
//    }
//
//    public List<Thread> showUserTechnologyThreadList(User user) {
//        List<Thread> threadsTechnology = new ArrayList<>();
//        for (Thread thread : user.getThreads()) {
//            if (thread.getCategory().equals("Technolgoy")) {
//                threadsTechnology.add(thread);
//            }
//        }
//        return threadsTechnology;
//    }
}


//    public List<Post> ShowAllPostWinningAwards(User user) {
//        List<Post> finalPostWinningUsers = new ArrayList<>();
//        Map<User,List<Post>> userMap=winningThreadInfoService.winningThreadParticipantsUsers();
//        return userMap.get(user);
//
//    }


