package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.ThreadWinner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadWinnerRepository extends CrudRepository<ThreadWinner, Long> {

}
