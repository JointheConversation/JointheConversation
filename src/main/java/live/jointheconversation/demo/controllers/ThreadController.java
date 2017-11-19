package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.services.ThreadService;
import live.jointheconversation.demo.services.UserOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ThreadController {
    private final ThreadService threadService;
    private final UserOwnerService userOwnerService;

    @Autowired
    public ThreadController(ThreadService threadService, UserOwnerService userOwnerService){
        this.threadService=threadService;
        this.userOwnerService=userOwnerService;
    }
    //These Getmappings will show thread information for all and single threads
    @GetMapping("/categories/threads")
    public String showAllThreads(Model viewModel){
        viewModel.addAttribute("threads", threadService.findAll());
        return "threads/index";
    }
    @GetMapping("/threads/{id}")
    public String singleThread(@PathVariable long id, Model viewModel){
        Thread thread=threadService.findById(id);
        if(userOwnerService.isOwner(thread)){
            viewModel.addAttribute("createduser",true);
        }
        viewModel.addAttribute("post",threadService.findById(id));
        return "threads/show";

    }
    //This postMapping will delete a thread if the user is user who created it.
    @PostMapping("/categories/threads/{id}/delete")
    public String deleteThread(@PathVariable long id){
        threadService.delete(id);
        return "redirect:/categories/threads";
    }

    //This post and get mapping will take care of edits to threads based on user ownership
    @GetMapping("/categories/threads/{id}/edit")
    public String threadEdit(@PathVariable long id, Model viewModel, UserOwnerService userOwnerService){
        Thread thread=threadService.findById(id);
        if(!userOwnerService.isOwner(thread)){
            return "redirect:/categories/threads/"+id;
        }
        viewModel.addAttribute("thread", threadService.findById(id));
        return "threads/edit";
    }

    @PostMapping("/categories/threads/{id}/edit")
    public String submitEdit(
            @PathVariable long id,
            @ModelAttribute Thread thread,
            Model viewModel
    ){
        thread.setId(id);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        thread.setUser(user);
        threadService.save(thread);
        return "redirect:/categories/threads/"+thread.getId();
    }

    //This post and get mapping will allow users to create discussion threads
    @GetMapping("/categories/threads/create")
    public String viewThreadForm(Model viewModel){
        viewModel.addAttribute("thread",new Thread());
        return "thread/create";
    }

    @PostMapping("/categories/threads/create")
    public String submitThreadForm(
            @Valid Thread thread,
            Errors validation,
            Model model
    ){
        if(validation.hasErrors()){
            model.addAttribute("errors",validation);
            model.addAttribute("thread",thread);
            return "threads/create";
        }
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        thread.setUser(user);
        threadService.save(thread);
        return "redirect:/categories/threads";
    }

}
