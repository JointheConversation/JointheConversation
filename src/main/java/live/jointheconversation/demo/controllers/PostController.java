package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.services.PostService;
import live.jointheconversation.demo.services.UploadCheckService;
import live.jointheconversation.demo.services.UserOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
public class PostController {
    private PostService postService;
    private final UserOwnerService userOwnerService;
    private final UploadCheckService uploadCheckService;

    // Shows posts
    @Autowired
    public PostController(PostService postService, UserOwnerService userOwnerService, UploadCheckService uploadCheckService){
        this.postService=postService;
        this.userOwnerService=userOwnerService;
        this.uploadCheckService=uploadCheckService;
    }

    //This getmapping will allow users to view all posts within a given thread
    @GetMapping("/categories/{categoryName}/threads/{threadId}/posts")
    public String showAllPosts(Model viewModel, @PathVariable long threadId, @PathVariable String categoryName){
        viewModel.addAttribute("posts",postService.findAll());
        return "posts/index";
    }

    @GetMapping("/categories/{categoryName}/threads/{threadId}/posts/{id}")
    public String singlePost(@PathVariable long threadId, @PathVariable long id, Model viewModel,@PathVariable String categoryName){
        Post post=postService.findById(id);
        if(userOwnerService.isOwner(post)){
            viewModel.addAttribute("createduser",true);
        }
        viewModel.addAttribute("post", postService.findById(id));
        return "posts/show";
    }

    //Creates Posts

    //This getmapping and postmapping relationship will allow users to create posts within a their given thread
    @GetMapping("/categories/{categoryName}/threads/{Id}/posts/create")
    public String viewPostForm(
            Model viewModel,
            @PathVariable String categoryName,
            Thread thread,
            @RequestParam(name = "file") MultipartFile uploadedFile

    )
    {
        if(!thread.getActiveStatus()){
            //Checks to see if the thread is still active before posting.

            return "redirect:/categories/threads";
        }
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
            Thread thread,
            @RequestParam(name = "file") MultipartFile uploadedFile
    ){

        if(!thread.getActiveStatus()){
            //Checks to see if the thread is still active before posting.
            return "redirect:/categories/threads";
        }

        post.setId(id);
        if(!userOwnerService.isOwner(post)){
            return "redirect:/categories/threads/{threadId}/posts/" +id;
        }
        uploadCheckService.UploadValidation(uploadedFile,viewModel,post);
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
                               @PathVariable long id)
    {
        postService.delete(id);
        return "redirect:/categories/{categoryName}/threads/{threadId}/posts";
    }


}
