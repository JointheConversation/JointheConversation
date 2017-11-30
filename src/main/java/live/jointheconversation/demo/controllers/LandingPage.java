package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.repositories.ThreadWinnerRepository;
import live.jointheconversation.demo.repositories.UserRepository;
import live.jointheconversation.demo.services.WinningThreadInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LandingPage {
    private ThreadWinnerRepository threadWinnerDao;
    private ThreadRepository threadDao;
    private UserRepository userDao;
    private WinningThreadInfoService winningThreadInfoService;

    public LandingPage(ThreadWinnerRepository threadWinnerDao, ThreadRepository threadDao, UserRepository userDao, WinningThreadInfoService winningThreadInfoService){
        this.threadDao=threadDao;
        this.threadWinnerDao=threadWinnerDao;
        this.userDao=userDao;
        this.winningThreadInfoService=winningThreadInfoService;
    }

    @GetMapping("/")
    public String landingPage(Model viewModel){
        System.out.println("It passed through the Landing controller");
        Thread thread = winningThreadInfoService.RetrieveThreadWinnerInfo();
        viewModel.addAttribute("thread", thread);
        return "index";
    }
}
