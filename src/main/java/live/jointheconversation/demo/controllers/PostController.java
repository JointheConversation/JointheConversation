package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.*;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.repositories.PostRepository;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PostController {
    private PostService postService;
    private final UserOwnerService userOwnerService;
    private final UploadCheckService uploadCheckService;
    private final CategoryService categoryService;
    private final ThreadService threadService;
    private PostRepository postDao;
    private ThreadCountService threadCountService;
    private ThreadRepository threadDao;

    // Shows posts
    @Autowired
    public PostController(PostService postService, UserOwnerService userOwnerService, UploadCheckService uploadCheckService, ThreadService threadService, CategoryService categoryService, PostRepository postDao, ThreadRepository threadDao, ThreadCountService threadCountService){
        this.postService=postService;
        this.userOwnerService=userOwnerService;
        this.uploadCheckService=uploadCheckService;
        this.threadService=threadService;
        this.categoryService=categoryService;
        this.postDao=postDao;
        this.threadDao=threadDao;
        this.threadCountService=threadCountService;
    }

    //This getmapping will allow users to view all posts within a given thread
    @GetMapping("/categories/{categoryName}/threads/{threadId}/posts")
    public String showAllPosts(Model viewModel, @PathVariable long threadId, @PathVariable String categoryName){
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        Thread thread=threadService.findById(threadId);
        viewModel.addAttribute("thread",thread);
        viewModel.addAttribute("posts",postDao.findByThread(thread));
        return "posts/index";
    }
    //May not need this.
//    @GetMapping("/categories/{categoryName}/threads/{threadId}/posts/{id}")
//    public String singlePost(@PathVariable long threadId, @PathVariable long id, Model viewModel,@PathVariable String categoryName){
//        Post post=postService.findById(id);
//        if(userOwnerService.isOwner(post)){
//            viewModel.addAttribute("createduser",true);
//        }
//        Category category=categoryService.findByTitle(categoryName);
//        viewModel.addAttribute("category",category);
//        Thread thread=threadService.findById(threadId);
//        viewModel.addAttribute("thread",thread);
//        viewModel.addAttribute("post", postService.findById(id));
//        return "posts/show";
//    }

    //Creates Posts

    //This getmapping and postmapping relationship will allow users to create posts within a their given thread
    @GetMapping("/categories/{categoryName}/threads/{id}/posts")
    public String viewPostForm(
            Model viewModel,
            @PathVariable long id,
            @PathVariable String categoryName,
            @RequestParam(name = "file") MultipartFile uploadedFile

    )
    {
        Thread thread=threadService.findById(id);
        if(!thread.getActiveStatus()){
            //Checks to see if the thread is still active before posting.

            return "redirect:/categories/{categoryName}/threads";
        }
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        viewModel.addAttribute("thread",thread);
        viewModel.addAttribute("post",new Post());
        return "posts/create";
    }

    @PostMapping("/categories/{categoryName}/threads/{threadId}/posts/create")
    public String createPost(
            @PathVariable long threadId,
            @PathVariable String categoryName,
            @Valid Post post,
            Errors validation,
            Model viewModel,
            @RequestParam(name = "file") MultipartFile uploadedFile
    ) {
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        Thread thread=threadService.findById(threadId);
        viewModel.addAttribute("thread",thread);
        if(validation.hasErrors()){
            viewModel.addAttribute("errors",validation);
            viewModel.addAttribute("post",post);
            return "posts/create";
        }
        uploadCheckService.UploadValidation(uploadedFile,viewModel,post);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        postService.save(post);
        return "redirect:/categories/threads/{id}/posts";
    }


    //Edits Posts
    @GetMapping("/categories/{categoryName}/threads/{threadId}/posts/{id}/edit")
    public String postEdit(
            @PathVariable long threadId,
            @PathVariable String categoryName,
            @PathVariable long id,
            @ModelAttribute Post post,
            Model viewModel,
            UserOwnerService userOwnerService,
            @RequestParam(name = "file") MultipartFile uploadedFile
    ){
        Thread thread=threadService.findById(threadId);


        if(!thread.getActiveStatus()){
            //Checks to see if the thread is still active before posting.
            return "redirect:/categories/threads";
        }

        post.setId(id);
        if(!userOwnerService.isOwner(post)){
            return "redirect:/categories/threads/{threadId}/posts/" +id;
        }
        uploadCheckService.UploadValidation(uploadedFile,viewModel,post);
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        viewModel.addAttribute("post", postService.findById(id));
        return "posts/edit";
    }
    @PostMapping("/categories/{categoryName}/threads/{threadId}/posts/{id}/edit")
    public String submitEdit(
            @PathVariable long threadId,
            @PathVariable String categoryName,
            @PathVariable long id,
            @ModelAttribute Post post,
            Model viewModel
    ){
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        Thread thread=threadService.findById(threadId);
        viewModel.addAttribute("thread",thread);
        post.setId(id);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        postService.save(post);
        return "redirect:/categories/{categoryName}/threads/{threadId}/posts/" + post.getId();
    }

    //Destroys Posts
    @PostMapping("/categories/{categoryName}/threads/{threadId}/{threadId}/posts/{id}/delete")
    public String submitDelete(@PathVariable long threadId,
                               @PathVariable String categoryName,
                               @PathVariable long id,
                               Model viewModel)
    {
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        Thread thread=threadService.findById(id);
        viewModel.addAttribute("thread",threadService.findById(id));
        postService.delete(id);
        return "redirect:/categories/{categoryName}/threads/{threadId}/posts";
    }


    @GetMapping("/posts.json")
    @ResponseBody
    public Iterable<Post> viewAllWinningPostsInJSONFormat(){
        List<ThreadCount> threadCounts = threadDao.countPostsInThreads();
        Thread thread = threadCountService.firstPlaceThread(threadCounts);
        System.out.println("Is the livefeed example the displayed thread is " + thread.getTitle());
        List<Post> posts = postDao.findByThread(thread);
                return posts;
    }
//    @GetMapping("/posts/ajax")
//    public String viewAllAdsWithAjax(){
//        return "posts/ajax1";
//    }


}
