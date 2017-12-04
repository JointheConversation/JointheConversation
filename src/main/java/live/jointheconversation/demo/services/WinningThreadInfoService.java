package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.ThreadWinner;
import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.repositories.PostRepository;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.repositories.ThreadWinnerRepository;
import live.jointheconversation.demo.repositories.UserRepository;
import org.mockito.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class WinningThreadInfoService {
    private ThreadWinnerRepository threadWinnerDao;
    private ThreadRepository threadDao;
    private UserRepository userDao;
    private PostRepository postDao;

    public WinningThreadInfoService(ThreadWinnerRepository threadWinnerDao, ThreadRepository threadDao, UserRepository userDao, PostRepository postDao) {
        this.threadDao = threadDao;
        this.threadWinnerDao = threadWinnerDao;
        this.userDao = userDao;
        this.postDao = postDao;
    }

    //Finds the thread winning thread
    public Thread RetrieveThreadWinnerInfo() {
        Iterable<ThreadWinner> threadWinners = threadWinnerDao.findAll();
        for (ThreadWinner threadWinner : threadWinners) {
            if (threadWinner.isActiveStatus()) {
                Thread thread = threadDao.findByThreadWinners(threadWinner);
//                System.out.println("The id number for winning thread is "+id);
//                Thread thread=threadDao.findOne(id);
                return thread;
            }
        }
        return null;
    }

    //This code will check the database for the Winning Threads and return a list of threads that won.
    public List<Thread> winningThreadParticipantsThreads() {
        Iterable<ThreadWinner> threadWinners = threadWinnerDao.findAll();
        List<Thread> threads = new ArrayList<>();
        for (ThreadWinner threadWinner : threadWinners) {
            threads.add(threadDao.findByThreadWinners(threadWinner));
            //Checks to see if there are no winner if not don't return anything.
            if (threads.isEmpty()) {
                return null;
            }
        }
        return threads;
//        return null;
    }


//    public List<Post> winningThreadParticipantsPost(){
//        Iterable<Thread> threads=winningThreadParticipantsThreads();
//        List<Post> posts=new ArrayList<>();
//        for(Thread thread:threads){
//            posts=postDao.findByThread(thread);
//        }
//        return posts;
//    }


    //This code will be used in conjunction with the winningThreadParticipantsThreads in order to generate a list of users who participated in the Thread.

    public List<Post> winningThreadParticipantsUsers() {
        //Creates a list of threads that have won.
        List<Thread> threads = winningThreadParticipantsThreads();
        //Creates an empty list of posts.
        List<Post> postsUser = new ArrayList<>();
        //Creates an empty collection of a list of posts.
        List<Post> postsWin = new ArrayList<>();


        //Within that loop loops through the winning threads
        for (Thread thread : threads) {
            //Loops through each user in the database

            for (User userPost : userDao.findAll()) {

                //Adds the posts in a winning thread from each user into a List of posts.
                postsUser.add(postDao.findByUser(userPost));
                if (postsUser.isEmpty()) {
                    return null;
                } else {
                    //Gets all posts from the winning thread
                    List<Post> postsThread = (postDao.findByThread(thread));
                    for (int i = 0; i < postsThread.size(); i++) {
                        if (postsUser.contains(postsThread)) {
                            System.out.println("the forloop of WTIS");
                            return postsUser;
                        }
                    }
                    System.out.println("the first null of WTIS");

                    return null;
                }

            }
            System.out.println("the second null of WTIS");

            return null;
        }
        System.out.println("the third null of WTIS");
        return null;
    }
}
