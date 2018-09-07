package shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import shop.dao.PromoDao;
import shop.entity.Promo;
import shop.model.ImageControl;

@Controller
@RequestMapping("/admin/promos")
public class AdminPromosController {
 
    @Autowired
    private PromoDao promoDao;
    @Autowired
    private ImageControl imageControl;

    @GetMapping({"", "/"})
    public String getListPage(Model model) {
        model.addAttribute("promos", promoDao.getList());
        return "admin.promos";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("title", "Новая промо-акция");
        model.addAttribute("promo", new Promo());
        model.addAttribute("callback", "add");
        return "admin.promo.edit";
    }

    @PostMapping("/add")
    public String add(Promo promo,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("promo", ext, imageControl.getDirectory("promo/"));

                if (imageControl.saveFile(tempFile, file)) {
                    promo.setFilename(tempFile.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminPromosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        promoDao.create(promo);
        return "redirect:/admin/promos";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") String id, Model model) {
        Promo promo = promoDao.findById(Long.parseLong(id));
        model.addAttribute("title", "Редактирование промо-акции");
        model.addAttribute("promo", promo);
        model.addAttribute("callback", "edit");
        return "admin.promo.edit";
    }

    @PostMapping("/edit")
    public String update(Promo promo,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            imageControl.removeFile(promo.getFilename());
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("promo", ext, imageControl.getDirectory("promo/"));

                if (imageControl.saveFile(tempFile, file)) {
                    promo.setFilename(tempFile.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminPromosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        promoDao.update(promo);
        return "redirect:/admin/promos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        Promo promo = promoDao.findById(Long.parseLong(id));
        if (promo != null) {
            imageControl.removeFile(promo.getFilename());
            promoDao.delete(promo.getId());
        }
        return "redirect:/admin/promos";
    }    
}
