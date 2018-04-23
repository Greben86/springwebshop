package shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class AppController {
 
    @GetMapping({ "/", "/home" })
    public String homePage(Model model) {
        return "homePage";
    }
 
     
    @GetMapping("/contactus")
    public String contactusPage(Model model) {
        model.addAttribute("address", "Vietnam");
        model.addAttribute("phone", "...");
        model.addAttribute("email", "...");
        return "contactusPage";
    }
     
}
