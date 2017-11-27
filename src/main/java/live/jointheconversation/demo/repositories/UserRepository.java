package live.jointheconversation.demo.repositories;
import live.jointheconversation.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    public User findByUsername(String username);
    public User findByEmail(String email);
    public String getPassword(String password);
}
