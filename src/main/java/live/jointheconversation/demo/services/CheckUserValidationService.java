package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class CheckUserValidationService {
    private UserRepository usersDao;

    public CheckUserValidationService(UserRepository users) {
        this.usersDao = usersDao;
    }
    public void validateCredentials(Errors validation, User user) {
        User correctUsername = usersDao.findByUsername(user.getUsername().trim());
        User correctPassword = usersDao.findByPassword(user.getPassword());
        if (!correctUsername.equals(usersDao.findByUsername(user.getUsername().trim()))) {
            validation.rejectValue(
                    "username",
                    "user.username",
                    "Incorrect username"
            );
        }
        if (!correctPassword.equals(usersDao.findByPassword(user.getPassword().trim()))) {
            validation.rejectValue(
                    "password",
                    "user.password",
                    "Incorrect password."
            );
        }
    }
}

