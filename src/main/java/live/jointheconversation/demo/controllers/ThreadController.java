package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.services.ThreadService;
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
public class ThreadController {
    private final ThreadService threadService;
    private final UserOwnerService userOwnerService;
    private final UploadCheckService uploadCheckService;

    @Autowired
    public ThreadController(ThreadService threadService, UserOwnerService userOwnerService, UploadCheckService uploadCheckService){
        this.threadService=threadService;
        this.userOwnerService=userOwnerService;
        this.uploadCheckService=uploadCheckService;
    }
    //These Getmappings will show thread information for all and single threads
    @GetMapping("/categories/{categoryName}/threads")
    public String showAllThreads(Model viewModel,@PathVariable String categoryName){
        viewModel.addAttribute("threads", threadService.findAll());
        return "threads/index";
    }
    @GetMapping("/categories/{categoryName}/threads/{id}")
    public String singleThread(@PathVariable long id, Model viewModel,@PathVariable String categoryName){
        Thread thread=threadService.findById(id);
        if(userOwnerService.isOwner(thread)){
            viewModel.addAttribute("createduser",true);
        }
        viewModel.addAttribute("post",threadService.findById(id));
        return "threads/show";

    }
    //This postMapping will delete a thread if the user is user who created it.
    @PostMapping("/categories/{categoryName}/threads/{id}/delete")
    public String deleteThread(@PathVariable long id, @PathVariable String categoryName){
        threadService.delete(id);
        return "redirect:/categories/{categoryName}/threads";
    }

    //This post and get mapping will take care of edits to threads based on user ownership
    @GetMapping("/categories/{categoryName}/threads/{id}/edit")
    public String threadEdit(@PathVariable long id,@PathVariable String categoryName, Model viewModel, UserOwnerService userOwnerService){
        Thread thread=threadService.findById(id);
        if(!userOwnerService.isOwner(thread)){
            return "redirect:/categories/{categoryName}/threads/"+id;
        }
        viewModel.addAttribute("thread", threadService.findById(id));
        return "threads/edit";
    }
        //Threads should be allowed to be edited.
//    @PostMapping("/categories/{categoryName}/threads/{id}/edit")
//    public String submitEdit(
//            @PathVariable String categoryName,
//            @PathVariable long id,
//            @ModelAttribute Thread thread,
//            Model viewModel,
//            @RequestParam(name = "file") MultipartFile uploadedFile
//    ){
//        thread.setId(id);
//        uploadCheckService.UploadValidation(uploadedFile,viewModel,thread);
//        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        thread.setUser(user);
//        threadService.save(thread);
//        return "redirect:/categories/{categoryName}/threads/"+thread.getId();
//    }

    //This post and get mapping will allow users to create discussion threads
    @GetMapping("/categories/{categoryName}/threads/create")
    public String viewThreadForm(Model viewModel, @PathVariable String categoryName){
        viewModel.addAttribute("thread",new Thread());
        return "thread/create";
    }

    @PostMapping("/categories/{categoryName}/threads/create")
    public String submitThreadForm(
            @PathVariable String categoryName,
            @Valid Thread thread,
            Errors validation,
            Model model,
            @RequestParam(name = "file") MultipartFile uploadedFile

    ){
        model.addAttribute("media", false);
        if(validation.hasErrors()){
            model.addAttribute("errors",validation);
            model.addAttribute("thread",thread);
            return "threads/create";
        }
        uploadCheckService.UploadValidation(uploadedFile,model,thread);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        thread.setUser(user);
        threadService.save(thread);
        return "redirect:/categories/{categoryName}/threads";
    }

}
