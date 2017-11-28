package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class CreateUserValidationService {
    private UserRepository usersDao;
    public CreateUserValidationService(UserRepository usersDao) {
        this.usersDao = usersDao;
    }
    public void validateDuplicate(Errors validation, User user) {
        User duplicateUsername = usersDao.findByUsername(user.getUsername().trim());
        User duplicateEmail=usersDao.findByEmail(user.getEmail().trim());

        if(duplicateUsername!=null){
            validation.rejectValue(
                    "username",
                    "user.username",
                    "A user with that username already exists."
            );
        }
        if(duplicateEmail!=null){
            validation.rejectValue(
                    "email",
                    "user.email",
                    "A user with that email already exists."
            );
        }
    }

    public void checkBlankSpace(Errors validation, User user){

        String usernameBlankSpace=user.getUsername();
        String emailBlankSpace=user.getEmail();
        String passwordBlankSpace=user.getPassword();

        if(!usernameBlankSpace.equals(usernameBlankSpace.trim())){
            validation.rejectValue(
                    "username",
                    "user.username",
                    "You cannot have any Blank Spaces in this field"
            );
        }
        if(!emailBlankSpace.equals(emailBlankSpace.trim())){
            validation.rejectValue(
                    "email",
                    "user.email",
                    "You cannot have any Blank Spaces in this field"
            );

        }
        if(!passwordBlankSpace.equals(passwordBlankSpace.trim())){
            validation.rejectValue(
                    "password",
                    "user.password",
                    "You cannot have any Blank Spaces in this field"
            );

        }

    }

}
