package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserOwnerService {
    public boolean isOwner(Post post) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return false;
        }

        User user = (User) principal;
        if (!post.getUser().getUsername().equals(user.getUsername())) {
            return false;
        }
        return true;
    }

    public boolean isOwner(Thread thread) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return false;
        }
        User user = (User) principal;
        if (!thread.getUser().getUsername().equals(user.getUsername())) {
            return false;

        }
        return true;
    }
}
