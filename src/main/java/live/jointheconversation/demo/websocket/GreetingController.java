//package live.jointheconversation.demo.websocket;

        import live.jointheconversation.demo.models.Post;
        import live.jointheconversation.demo.models.Thread;
        import live.jointheconversation.demo.models.ThreadCount;
        import live.jointheconversation.demo.repositories.PostRepository;
        import live.jointheconversation.demo.repositories.ThreadRepository;
        import live.jointheconversation.demo.services.ThreadCountService;
        import live.jointheconversation.demo.websocket.Greeting;
        import live.jointheconversation.demo.websocket.HelloMessage;
        import org.springframework.messaging.handler.annotation.MessageMapping;
        import org.springframework.messaging.handler.annotation.SendTo;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;

        import java.util.List;

//        @Controller
// public class GreetingController {
//            private ThreadRepository threadDao;
//            private PostRepository postDao;
//            private ThreadCountService threadCountService;
//
//            public GreetingController(ThreadRepository threadDao, PostRepository PostDao, ThreadCountService threadCountService) {
//                this.postDao = postDao;
//                this.threadDao = threadDao;
//                this.threadCountService = threadCountService;
//            }
//
//            @GetMapping("/test")
//            public String test() {
//                return "liveFeeds/index";
//            }
//
//            //    @MessageMapping("/hello")
//            //    @SendTo("/topic/greetings")
//            public Greeting greeting(HelloMessage message) throws Exception {
//                java.lang.Thread.sleep(1000);
//                return new Greeting("Hello, " + message.getName() + "!");
//
//
//            }
//
//            @MessageMapping("/hello")
//            @SendTo("/topic/greetings")
//            public Thread LiveThreadPosts(Model model) throws Exception{
//                //This thread sleep is different from our custom model Thread
//
//                 java.lang.Thread.sleep(1000);
//                List<ThreadCount> threadCounts = threadDao.countPostsInThreads();
//                Thread thread=threadCountService.firstPlaceThread(threadCounts);
//                List<Post> posts=postDao.findByThread(thread);
//                model.addAttribute("thread1", thread);
//                model.addAttribute("posts",posts);
//                return null;
//
//                    }

//         }