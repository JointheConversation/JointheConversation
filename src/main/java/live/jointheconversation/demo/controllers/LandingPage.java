package live.jointheconversation.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LandingPage {

    @GetMapping("/")
    public String landingPage( Model viewModel ){
        viewModel.addAttribute("Welcome", null);
        return "index";
    }
}
