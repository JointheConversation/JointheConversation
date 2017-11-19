package live.jointheconversation.demo.repositories;

import live.jointheconversation.demo.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByTitle(String title);
}
