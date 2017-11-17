package live.jointheconversation.demo.repositories;

import org.springframework.data.repository.CrudRepository;

public interface ThreadRepository extends CrudRepository<Thread, Long>{
    Thread findByTitle(String title);
}
