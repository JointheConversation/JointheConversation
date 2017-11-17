package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post,Long>{
    Post findByTitle(String title);
    Post findById(long id);
}
