package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.dao.NewsDao;
import shop.dao.PartnerDao;
import shop.dao.PromoDao;
import shop.dao.RequestDao;
import shop.entity.News;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RequestDao requestDao;
    @Autowired
    private PartnerDao partnerDao;
    @Autowired
    private PromoDao promoDao;
    @Autowired
    private NewsDao newsDao;

    @GetMapping({"/", "/requests"})
    public String getPageApplications(Model model) {
        model.addAttribute("requests", requestDao.getList());
        return "admin.requests";
    }

    @GetMapping("/partners")
    public String getPagePartners(Model model) {
        model.addAttribute("partners", partnerDao.getList());
        return "admin.partners";
    }

    @GetMapping("/promos")
    public String getPagePromos(Model model) {
        model.addAttribute("promos", promoDao.getList());
        return "admin.promos";
    }

    @GetMapping("/news")
    public String getPageNews(Model model) {
        model.addAttribute("news", newsDao.getList());
        return "admin.news";
    }
    
    @GetMapping("/news/add")
    public String getPageAddNews(Model model) {
        model.addAttribute("news", new News());
        return "admin.news.edit";
    }
    
    @PostMapping("/news/add")
    public String addNews(News news) {
        return "redirect:/admin/news";
    }
    
    @GetMapping("/news/delete/{id}")
    public String deleteNews(@PathVariable String id) {
        newsDao.delete(Long.parseLong(id));
        return "redirect:/admin/news";
    }
}
