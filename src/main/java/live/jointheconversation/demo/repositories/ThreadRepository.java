package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.ThreadCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long>{
    Thread findByTitle(String title);
    Thread save(Thread thread);

    /*
    select count(*) as 'Number of Posts', title from threads as thread
  join posts as posts on thread.id = posts.thread_id  && thread.active_status=1 GROUP BY title order by count(*) DESC LIMIT 3;
     */
    // THis statement allows us to access the information from the mysql databse and save it to a model ThreadCount.
    @Query("select new live.jointheconversation.demo.models.ThreadCount(count(p.id), t.title) from Thread t join t.posts p where t.activeStatus =1 group by t.title order by count(t) desc")
    List<ThreadCount> countPostsInThreads();
}
