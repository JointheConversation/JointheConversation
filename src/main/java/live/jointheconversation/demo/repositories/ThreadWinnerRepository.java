package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.ThreadWinner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadWinnerRepository extends CrudRepository<ThreadWinner, Long> {
    ThreadWinner findByThreadId(long thread_id);
    ThreadWinner findByThread(Thread thread);
    ThreadWinner findByActiveStatus(Boolean statement);

}
