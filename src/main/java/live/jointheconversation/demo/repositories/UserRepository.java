package live.jointheconversation.demo.repositories;
import live.jointheconversation.demo.models.Post;

import live.jointheconversation.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    public User findByUsername(String username);
    public User findByEmail(String email);
    List <User> findByPosts(Collection<List<Post>> postss);
//    User findByPosts(List<Post> posts);
//    List<User> findByThreadsAndAndPosts(List<Thread> threads, List<Post> posts);
}

