package live.jointheconversation.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LandingPage {

    @GetMapping("/")
    @ResponseBody
    public String landingPage(){
        return "Here is the landing page";
    }
}
