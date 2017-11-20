package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postDao;
//    private List<Post> posts;

    @Autowired
    public PostService(PostRepository postDao) {
        this.postDao=postDao;
    }

    public Post save(Post post) {
        return postDao.save(post);
    }
    public Iterable<Post> findAll() {
        return postDao.findAll();
    }

    public Post findById(long id) {
        return postDao.findOne(id);
    }

//    public void update(Post post){
//        posts.set((int) post.getId()-1, post);
//    }
    public void delete(long id) {
        postDao.delete(id);
    }
}
