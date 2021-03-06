package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.dao.RequestDao;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RequestDao requestDao;

    @GetMapping({"", "/", "/requests"})
    public String getPageApplications(Model model) {
        model.addAttribute("requests", requestDao.getList());
        return "admin.requests";
    }
}
