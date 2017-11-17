package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Long>{
    Post findByTitle(String title);
}
