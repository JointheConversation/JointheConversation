package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.*;
import live.jointheconversation.demo.models.Thread;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
    Thread findByTitle(String title);

    Thread save(Thread thread);

    List<Thread> findByCategory(Category category);


    /*
    select count(*) as 'Number of Posts', title from threads as thread
  join posts as posts on thread.id = posts.thread_id  && thread.active_status=1 GROUP BY title order by count(*) DESC LIMIT 3;
     */
    // THis statement allows us to access the information from the mysql databse and save it to a model ThreadCount.
    @Query("select new live.jointheconversation.demo.models.ThreadCount(count(p.id), t.title, t.id) from Thread t join t.posts p where t.activeStatus =1 group by t.title, t.id order by count(t) desc")
    List<ThreadCount> countPostsInThreads();

    //Checks winning threads and creates a list of threads that were created by the User
    @Query("select t from ThreadWinner w join w.thread t join t.user u where u = ?1")
    List<Thread> findAllWinnerThreadsOfUser(User user);


    //    @Query("select t from ThreadWinner w join t where w.activeStatus =1")
//    @Query("select t from ThreadWinner w where w=?1")
//    Thread findActiveWinningThread(ThreadWinner threadWinner);
    Thread findByThreadWinners(ThreadWinner threadWinner);
}

//    @Query("select t from ThreadWinner w join w.thread t join t.thread where u=?1")

    //This bottom code will compare the threads from their winning categories.
//    @Query("select t from ThreadWinner w join w.thread.... ")
//    List<Thread> findAllWinnerCategoryThreadsOfUser(User user);

