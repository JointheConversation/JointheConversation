package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.services.PostService;
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

    // Shows posts
    @Autowired
    public PostController(PostService postService, UserOwnerService userOwnerService){
        this.postService=postService;
        this.userOwnerService=userOwnerService;
    }

    //This getmapping will allow users to view all posts within a given thread
    @GetMapping("/thread/{threadId}/posts")
    public String showAllPosts(Model viewModel, @PathVariable long threadId){
        viewModel.addAttribute("posts",postService.findAll());
        return "posts/index";
    }

    @GetMapping("/thread/{threadId}/posts/{id}")
    public String singlePost(@PathVariable long threadId, @PathVariable long id, Model viewModel){
        Post post=postService.findById(id);
        if(userOwnerService.isOwner(post)){
            viewModel.addAttribute("createduser",true);
        }
        viewModel.addAttribute("post", postService.findById(id));
        return "posts/show";
    }

    //Creates Posts

    //This getmapping and postmapping relationship will allow users to create posts within a their given thread
    @GetMapping("/thread/{id}/posts/create")
    public String viewPostForm(Model viewModel){
        viewModel.addAttribute("post",new Post());
        return "posts/create";
    }

    @PostMapping("/thread/{threadId}/posts/create")
    public String createPost(
            @PathVariable long threadId,
            @Valid Post post,
            Errors validation,
            Model viewModel
    ) {
        if(validation.hasErrors()){
            viewModel.addAttribute("errors",validation);
            viewModel.addAttribute("post",post);
            return "posts/create";
        }
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        postService.save(post);
        return "redirect:/thread/{id}/posts";
    }


    //Edits Posts
    @GetMapping("/thread/{threadId}/posts{id}/edit")
    public String postEdit(@PathVariable long threadId, @PathVariable long id, Model viewModel, UserOwnerService userOwnerService){
        Post post=postService.findById(id);
        if(!userOwnerService.isOwner(post)){
            return "redirect:/thread/{threadId}/posts/" +id;
        }
        viewModel.addAttribute("post", postService.findById(id));
        return "posts/edit";
    }
    @PostMapping("/thread/{threadId}/posts{id}/edit")
    public String submitEdit(
            @PathVariable long threadId,
            @PathVariable long id,
            @ModelAttribute Post post,
            Model model
    ){
        post.setId(id);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    //Destroys Posts
    @PostMapping("/thread/{threadId}/posts/{id}/delete")
    public String submitDelete(@PathVariable long threadId, @PathVariable long id){
        postService.delete(id);
        return "redirect:/thread/{id}/posts";
    }


}
