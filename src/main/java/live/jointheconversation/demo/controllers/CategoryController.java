package live.jointheconversation.demo.controllers;

import live.jointheconversation.demo.models.Category;

import live.jointheconversation.demo.services.CategoryService;
import live.jointheconversation.demo.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class CategoryController {
    private final CategoryService categoryService;
//    private final ThreadService threadService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
//        this.threadService=threadService;
    }
    //Displays all categories on landing page.
    @GetMapping("/categories")
    public String showAllCategories(Model viewModel){
        viewModel.addAttribute("categories",categoryService.findAll());
        return "categories/index";
    }
    @GetMapping("/categories/{categoryName}")
    public String showSpecificCategory(
            @PathVariable String categoryName,
            Model viewModel
    ){
        Category category=categoryService.findByTitle(categoryName);
        viewModel.addAttribute("category",category);

        return "categories/show";
    }


}
