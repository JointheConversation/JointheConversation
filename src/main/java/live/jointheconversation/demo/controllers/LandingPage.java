package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.ThreadCount;
import live.jointheconversation.demo.repositories.ThreadRepository;
import live.jointheconversation.demo.repositories.ThreadWinnerRepository;
import live.jointheconversation.demo.repositories.UserRepository;
import live.jointheconversation.demo.services.ThreadCountService;
import live.jointheconversation.demo.services.WinningThreadInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class LandingPage {
    private ThreadWinnerRepository threadWinnerDao;
    private ThreadRepository threadDao;
    private UserRepository userDao;
    private WinningThreadInfoService winningThreadInfoService;
    private ThreadCountService threadCountService;

    public LandingPage(ThreadWinnerRepository threadWinnerDao, ThreadRepository threadDao, UserRepository userDao, WinningThreadInfoService winningThreadInfoService, ThreadCountService threadCountService){
        this.threadDao=threadDao;
        this.threadWinnerDao=threadWinnerDao;
        this.userDao=userDao;
        this.winningThreadInfoService=winningThreadInfoService;
        this.threadCountService=threadCountService;
    }

    @GetMapping("/")
    public String landingPage(Model viewModel){
        System.out.println("It passed through the Landing controller");
        Thread lastthreadwinner = winningThreadInfoService.RetrieveThreadWinnerInfo(); //Finds the winner of the last thread time period
        List<ThreadCount> threadCounts=threadDao.countPostsInThreads();
        Thread thread= threadCountService.firstPlaceThread(threadCounts);
        if(thread==null){
            viewModel.addAttribute("databaseThread",false);
        }
        viewModel.addAttribute("databaseThread",true);
        viewModel.addAttribute("threadwinner", lastthreadwinner);
        viewModel.addAttribute("thread", thread);
        return "index";
    }
}
