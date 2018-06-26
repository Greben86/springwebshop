package shop.controller;

import javax.servlet.http.HttpServletRequest;
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
    public String getPageApplications(Model model, HttpServletRequest request) {
        //HttpServletRequest httpReq = (HttpServletRequest) request;
//        String url = request.getRequestURI();
//        model.addAttribute("url", url);
        model.addAttribute("requests", requestDao.getList());
        return "admin.requests";
    }
}
