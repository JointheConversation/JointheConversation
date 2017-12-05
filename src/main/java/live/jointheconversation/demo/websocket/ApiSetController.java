package live.jointheconversation.demo.websocket;


import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.ThreadCount;
import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.repositories.PostRepository;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.services.ThreadCountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class ApiSetController {
    private PostRepository postDao;
    private ThreadCountService threadCountService;
    private ThreadRepository threadDao;
    public ApiSetController(PostRepository postDao, ThreadCountService threadCountService, ThreadRepository threadDao){
        this.postDao=postDao;
        this.threadCountService=threadCountService;
        this.threadDao=threadDao;
    }

//    List<Post> post = new ArrayList<Post>();

    @GetMapping(value = "/all")
    public Response getResource() {
        List<ThreadCount> threadCounts=threadDao.countPostsInThreads();
        Thread thread= threadCountService.firstPlaceThread(threadCounts);
        Response response = new Response("Done", postDao.findByThread(thread));
        return response;
    }

    @PostMapping(value = "/save/{threadId}/{description}")
    public Post savePost(@PathVariable Long threadId, @PathVariable String description) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Thread thread = threadDao.findOne(threadId);
        Post post = new Post();
        post.setThread(thread);
        post.setUser(user);
        post.setDescription(description);
        postDao.save(post);
        // Create Response Object
        //Response response = new Response("Done", post);
        return post;
    }

    @GetMapping(value="/thread/{threadId}")
    public Response getThreadThreadId(@PathVariable Long threadId){
        Thread thread=threadDao.findOne(threadId);
        Response response=new Response("Done", postDao.findByThread(thread));
        System.out.println(response);
        return response;
    }




}
