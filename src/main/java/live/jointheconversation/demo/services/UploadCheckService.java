package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Post;
import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service

public class UploadCheckService {

    @Value("${file-upload-path}")
    private String  uploadPath;

    @Value("${file-upload-path-user}")
    private String uploadPathUser;



    public void UploadValidation(MultipartFile uploadedFile, Model model, Post post){
        String filename = uploadedFile.getOriginalFilename();
        Path filepath = Paths.get(uploadPath, filename);
        File destinationFile = new File(filepath.toString());
        try {
            uploadedFile.transferTo(destinationFile);
            post.setImage_url_path(filepath.getFileName().toString());
            model.addAttribute("message", "File successfully uploaded!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Oops! Something went wrong! " + e);
        }
    }

    public void UploadValidation(MultipartFile uploadedFile, Model model, User user){
        String filename = uploadedFile.getOriginalFilename();
        Path filepath = Paths.get(uploadPathUser, filename);
        File destinationFile = new File(filepath.toString());
        try {
            uploadedFile.transferTo(destinationFile);
            user.setUserpic_path(filepath.getFileName().toString());
            model.addAttribute("message", "File successfully uploaded!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Oops! Something went wrong! " + e);
        }
    }
    public void UploadValidation(MultipartFile uploadedFile, Model model, Thread thread){
        String filename = uploadedFile.getOriginalFilename();
        Path filepath = Paths.get(uploadPath, filename);
        File destinationFile = new File(filepath.toString());
        try {
            uploadedFile.transferTo(destinationFile);
            thread.setImage_url_path(filepath.getFileName().toString());
            model.addAttribute("message", "File successfully uploaded!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Oops! Something went wrong! " + e);
        }
    }
}