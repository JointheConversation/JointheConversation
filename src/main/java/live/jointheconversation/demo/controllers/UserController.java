package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.*;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.repositories.UserRepository;
import live.jointheconversation.demo.services.CreateUserValidationService;
import live.jointheconversation.demo.services.ThreadCountService;
import live.jointheconversation.demo.services.UploadCheckService;
import live.jointheconversation.demo.services.UserThreadWinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private UserRepository usersDao;
    private CreateUserValidationService userValidationService;
    private PasswordEncoder passwordEncoder;
    private UserThreadWinsService userThreadWinsService;
    private UploadCheckService uploadCheckService;
    private ThreadCountService threadCountService;

    @Autowired
    public UserController(UserRepository usersDao, CreateUserValidationService userValidationService, PasswordEncoder passwordEncoder, UserThreadWinsService userThreadWinsService, UploadCheckService uploadCheckService, ThreadCountService threadCountService){
        this.usersDao=usersDao;
        this.userValidationService=userValidationService;
        this.passwordEncoder=passwordEncoder;
        this.userThreadWinsService= userThreadWinsService;
        this.uploadCheckService=uploadCheckService;
        this.threadCountService=threadCountService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "users/registration";
    }
    @PostMapping("/register")
    public String registerUser(
            @Valid User user,
            Errors validation,
            Model model,
            @RequestParam(name = "file") MultipartFile uploadedFile){
        userValidationService.validateDuplicate(validation, user);
        userValidationService.checkBlankSpace(validation, user);
        if(validation.hasErrors()){
            model.addAttribute("errors",validation);
            model.addAttribute("user",user);
            return "users/registration";
        }
        uploadCheckService.UploadValidation(uploadedFile,model,user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //BEncryptPassword goes here.
        usersDao.save(user);
        return "redirect:/login";

    }
    @GetMapping("/profile/{name}")
    public String goToUserProfile(
            @PathVariable String name,
            Model model
    ){

        User user=usersDao.findByUsername(name);
        List<Thread> threadAwards=userThreadWinsService.ShowAllThreadWinningAwards(user);


        model.addAttribute("threadAwards",threadAwards);


        model.addAttribute("user", user);
        model.addAttribute("threads", user.getThreads()); //Can create an if statement in the view if they have any threads to display them.
        model.addAttribute("posts", user.getPosts());
        //Same if statement can be applied here.
        return "users/profile";
    }

    @GetMapping("/aboutus")
    public String showAboutUs(Model model){
        return "users/aboutus";
    }


    @GetMapping("/users.json")
    @ResponseBody
    public Iterable<User> viewAllPostsInJSONFormat(){
        return usersDao.findAll();
    }

}
