package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.repositories.UserRepository;
import live.jointheconversation.demo.services.CreateUserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.Validation;

@Controller
public class LoginController {
    private UserRepository usersDao;
    private CreateUserValidationService userValidationService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserRepository usersDao, CreateUserValidationService userValidationService, PasswordEncoder passwordEncoder){
        this.usersDao=usersDao;
        this.userValidationService=userValidationService;
        this.passwordEncoder=passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "users/login";

    }

        @PostMapping("/login")
    public String loginUser( @PathVariable String username, String email, String password,
            @Valid User user,
            Errors validation,
            Model model) {
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            userValidationService.checkBlankSpace(validation, user);
            if (validation.hasErrors()) {
                model.addAttribute("errors", validation);
                return "users/registration";
            }
            String hash = usersDao.getPassword(hashCode());
            boolean validAttempt = BCrypt.checkpw(password, hash);
            if (validAttempt) {
              return "/profile";





    }
