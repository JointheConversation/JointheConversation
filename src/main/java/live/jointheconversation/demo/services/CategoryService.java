package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Category;
import live.jointheconversation.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryDao;
    private List<Category> categories;
@Autowired
    public CategoryService(CategoryRepository categoryDao) {
    createCategories();
    this.categoryDao=categoryDao;
}
public Category save(Category category){
    return categoryDao.save(category);
}
public Iterable<Category> findAll(){
    return categoryDao.findAll();
}
public Category findByTitle(String title){
    return categoryDao.findByTitle(title);
}
public void delete(long id) {
        categoryDao.delete(id);
    }

private void createCategories(){
    save(new Category("category 1","img_Path1"));
    save(new Category("category 2","img_Path2"));
    save(new Category("category 3","img_Path3"));
    save(new Category("category 4","img_Path4"));
    save(new Category("category 5","img_Path5"));
}
}
