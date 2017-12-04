package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.Category;
import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.repositories.PostRepository;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.services.CategoryService;
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
    private final CategoryService categoryService;
    private final ThreadRepository threadDao;
    private final PostRepository postDao;

    @Autowired
    public ThreadController(ThreadService threadService, UserOwnerService userOwnerService, UploadCheckService uploadCheckService, CategoryService categoryService, ThreadRepository threadDao, PostRepository postDao){
        this.threadService=threadService;
        this.userOwnerService=userOwnerService;
        this.uploadCheckService=uploadCheckService;
        this.categoryService=categoryService;
        this.threadDao=threadDao;
        this.postDao=postDao;
    }
    //These Getmappings will show thread information for all and single threads
    @GetMapping("/categories/{categoryName}/threads")
    public String showAllThreads(Model viewModel,@PathVariable String categoryName){
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        viewModel.addAttribute("threads", threadDao.findByCategory(category));
        return "threads/index";
    }
    @GetMapping("/categories/{categoryName}/threads/{id}")
    public String singleThread(@PathVariable long id, Model viewModel,@PathVariable String categoryName){
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        Thread thread=threadService.findById(id);

        if(userOwnerService.isOwner(thread)){
            viewModel.addAttribute("createduser",true);
        }
        viewModel.addAttribute("thread",thread);
        viewModel.addAttribute("posts",postDao.findByThread(thread));

        return "threads/show";

    }




    //This postMapping will delete a thread if the user is user who created it.
    @PostMapping("/categories/{categoryName}/threads/{id}/delete")
    public String deleteThread(@PathVariable long id, @PathVariable String categoryName){
        Category category=categoryService.findByTitle(categoryName);
        threadService.delete(id);
        return "redirect:/categories/{categoryName}/threads";
    }

    //This post and get mapping will take care of edits to threads based on user ownership
    @GetMapping("/categories/{categoryName}/threads/{id}/edit")
    public String threadEdit(@PathVariable long id,@PathVariable String categoryName, Model viewModel, UserOwnerService userOwnerService){
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
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
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);
        viewModel.addAttribute("thread",new Thread());
        return "threads/create";
    }

    @PostMapping("/categories/{categoryName}/threads/create")
    public String submitThreadForm(
            @PathVariable String categoryName,
            @Valid Thread thread,
            Errors validation,
            Model model,
            @RequestParam(name = "file") MultipartFile uploadedFile

    ){
        Category category=categoryService.findByTitle(categoryName);
        System.out.println(category.getTitle());
        if(validation.hasErrors()){
            model.addAttribute("errors",validation);
            model.addAttribute("thread",thread);
            return "threads/create";
        }
        System.out.println(category.getTitle());
        uploadCheckService.UploadValidation(uploadedFile,model,thread);
        model.addAttribute("category",category);
        model.addAttribute("media", false);


        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        thread.setCategory(category);
        thread.setUser(user);
        thread.setActiveStatus(true);
        threadService.save(thread);
        return "redirect:/categories/{categoryName}/threads";
    }

    @GetMapping("/threads.json")
    @ResponseBody
    public Iterable<Thread> viewAllPostsInJSONFormat(){
        return threadDao.findAll();
    }

}
