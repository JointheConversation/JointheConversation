package live.jointheconversation.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LandingPage {

    @GetMapping("/index")
    public String landingPage( Model viewModel ){
        viewModel.addAttribute("Converse", null);
        return "index";
    }
}
