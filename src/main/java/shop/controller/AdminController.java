package shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.dao.impl.RequestDaoImpl;
 
@Controller
@RequestMapping("/admin")
public class AdminController {
 
    @GetMapping({ "/", "/requests" })
    public String getPageApplications(Model model) {
        RequestDaoImpl dao = new RequestDaoImpl();
        model.addAttribute("requests", dao.getList(""));
        return "admin.requests";
    }
      
    @GetMapping("/programs")
    public String getPagePrograms(Model model) {
        return "admin.programs";
    }

    @GetMapping("/partners")
    public String getPagePartners(Model model) {
        return "admin.partners";
    }
    
    @GetMapping("/promos")
    public String getPagePromos(Model model) {
        return "admin.promos";
    }    
}
