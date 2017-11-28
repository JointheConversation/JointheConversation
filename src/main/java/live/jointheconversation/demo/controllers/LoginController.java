package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.services.CheckUserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {
//    private CheckUserValidationService userValidationService;
//
//    @Autowired
//    public LoginController(CheckUserValidationService userValidationService) {
//        this.userValidationService = userValidationService;
//
//    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

//    @PostMapping("/login")
//    public String loginUser(
//            @Valid User user,
//            Errors validation,
//            Model model) {
//        model.addAttribute("username", user);
//        userValidationService.validateCredentials(validation, user);
//        if (validation.hasErrors()) {
//            model.addAttribute("errors", validation);
//            return "users/login";
//        }
//        return "/profile";
//    }

}





