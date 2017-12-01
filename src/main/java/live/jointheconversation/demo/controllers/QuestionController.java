package live.jointheconversation.demo.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class QuestionController {

    @MessageMapping("/questions")
    @SendTo("/topic/questions")
    public String processQuestion(String question){
        return question;
    }
}
