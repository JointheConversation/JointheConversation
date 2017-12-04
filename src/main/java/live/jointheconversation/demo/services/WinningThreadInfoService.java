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

import java.util.*;

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

    //Finds the threadwinning threads
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

//    public Map<User,List<Post>> winningThreadParticipantsUsers() {
//        List<Long> winningThreadIds=WinningThreadId();
//        //Creates a list of threads that have won.
//        List<Thread> threads = winningThreadParticipantsThreads();
//        //Creates an empty list of posts.
//        List<Post> postsUser = new ArrayList<>();
//        //Creates an empty collection of a list of posts.
//        List<Post> postsWin = new ArrayList<>();
//        List<Post> postsThread=new ArrayList<>();
//        Map<User,List<Post>> userMap= new HashMap<User,List<Post>>();
//        //Within that loop loops through the winning threads
//        for (Long threadId : winningThreadIds) {
//            //Loops through each user in the database
//            postsThread.add((postDao.findByThreadId(threadId)));
//        }
//
//        for (User userPost : userDao.findAll()) {
//                //Adds the posts in a winning thread from each user into a List of posts.
//            postsUser.add(postDao.findByUser(userPost));
//        }
//
////        postsUser.sort(Comparator.comparing(Post::getId));
////        postsUser.sort(Comparator.comparing(Post::getId));
//
//
//
//        System.out.println("The size of postsUser "+postsUser.size());
//        System.out.println("The size of postsThread "+postsThread.size());
//
////        for(int i=0; i<postsUser.size(); i++){
////            System.out.println(postsUser.get(i).getDescription());
////        }
////
////        for(int i=0; i<postsThread.size(); i++){
////            System.out.println(postsThread.get(i).getDescription());
////        }
////                if (postsUser.isEmpty()) {
////                    System.out.println("Is postsUser empty: "+postsUser.isEmpty());
////                }
////                if(postsThread.isEmpty()){
////                    System.out.println("Is postsThread empty: "+postsThread.isEmpty());
////                }
//
//                    //Gets all posts from the winning thread
//        if(postsThread.size()>=postsUser.size()) {
//            System.out.println("PostThread is larger than Postuser");
////            System.out.println(postsThread.get(0).getDescription());
//            for (Post postUser:postsUser) {
//                if (postsThread.contains(postsUser)) {
//                    userMap.put(postUser.getUser(),postsThread);
////                   System.out.println("User winning threadlists posts" + postsWin.get(i).getDescription());
//                }
//                    break;
//
//            }
//        }
//
//        else if(postsThread.size()<postsUser.size()){
//            System.out.println("PostUser is larger than PostThreads");
////            System.out.println(postsUser.get(0).getDescription());
//            for (Post postThread:postsThread) {
//                if (postsUser.contains(postThread)) {
//                    userMap.put(postThread.getUser(),postsThread);
////                    System.out.println("User winning threadlists posts" + postsWin.get(i).getDescription());
//                }
//                break;
//            }
//
//        }
//                    System.out.println("The size of postsWin "+postsWin.size());
//                    return userMap;
//
//    }

    public List<Long> WinningThreadId(){
        List<Thread> threads=winningThreadParticipantsThreads();
        List<Long> threadWinID=new ArrayList<>();
        for (Thread thread:threads){
            threadWinID.add(threadWinnerDao.findByThreadId(thread.getId()).getId());
        }
            return threadWinID;
    }
}
