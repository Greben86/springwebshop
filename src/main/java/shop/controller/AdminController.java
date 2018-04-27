package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
}
