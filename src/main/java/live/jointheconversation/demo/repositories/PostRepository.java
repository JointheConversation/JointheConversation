package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post,Long>{
    Post findById(long id);
    List<Post> findByThread(Thread thread);
    List<Post> findByUser(User user);

//    List<Post> findByThread(Thread thread);
//    List<Post> findAllWinnerPostOfUser;

}
