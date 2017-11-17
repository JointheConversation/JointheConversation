package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.Thread;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long>{
    Thread findByTitle(String title);
    Thread save(Thread thread);
}
