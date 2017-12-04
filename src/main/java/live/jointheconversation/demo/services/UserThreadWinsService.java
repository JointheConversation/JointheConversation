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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserThreadWinsService {
    private ThreadWinnerRepository threadWinnerDao;
    private UserRepository userDao;
    private ThreadRepository threadDao;
    private UserOwnerService userOwnerService;
    private WinningThreadInfoService winningThreadInfoService;
    private PostRepository postDao;

    public UserThreadWinsService(ThreadWinnerRepository threadWinnerDao, UserRepository userDao, ThreadRepository threadDao, UserOwnerService userOwnerService,WinningThreadInfoService winningThreadInfoService,PostRepository postDao){
        this.threadDao=threadDao;
        this.threadWinnerDao=threadWinnerDao;
        this.userDao=userDao;
        this.userOwnerService=userOwnerService;
        this.winningThreadInfoService=winningThreadInfoService;
        this.postDao=postDao;
    }

    public List<Thread> ShowAllThreadWinningAwards(User user){
        List<Thread> threadsAwards=threadDao.findAllWinnerThreadsOfUser(user);
        return threadsAwards;
    }
    public List<Post> ShowAllPostWinningAwards(User user) {
//        List<Thread> threadsAwards=threadDao.findAllWinnerThreadsOfUser(user);
        List<Post> finalPostWinningUsers = new ArrayList<>();

        List<Post> posts = winningThreadInfoService.winningThreadParticipantsUsers();
        if (posts==null) {
            return null;
        } else {
            for (Post post : posts) {
                post = postDao.findByUser(user);
                finalPostWinningUsers.add(post);
            }

            System.out.println(finalPostWinningUsers.size());
            return finalPostWinningUsers;
        }
    }


}
