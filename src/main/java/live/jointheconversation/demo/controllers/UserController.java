package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.repositories.UserRepository;
import live.jointheconversation.demo.services.CreateUserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserRepository usersDao;
    private CreateUserValidationService userValidationService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository usersDao, PasswordEncoder passwordEncoder, CreateUserValidationService userValidationService){
        this.usersDao=usersDao;
        this.userValidationService=userValidationService;
        this.passwordEncoder=passwordEncoder;
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
            Model model){
        userValidationService.validateDuplicate(validation, user);
        userValidationService.checkBlankSpace(validation, user);
        if(validation.hasErrors()){
            model.addAttribute("errors",validation);
            model.addAttribute("user",user);
            return "users/registration";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDao.save(user);
        return "redirect:/login";

}
    @GetMapping("/profile/{name}")
    public String goToUserProfile(
            @PathVariable String name,
            Model model
    ){
        User user=usersDao.findByUsername(name);
        model.addAttribute("user", user);
        model.addAttribute("threads", user.getThreads()); //Can create an if statement in the view if they have any threads to display them.
        model.addAttribute("posts", user.getPosts());
        //Same if statement can be applied here.

        return "users/userProfile";
    }

}
