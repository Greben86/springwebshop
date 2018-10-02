package shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
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
import shop.dao.PartnerDao;
import shop.entity.Partner;
import shop.model.ImageControl;
import static java.util.Optional.ofNullable;

@Controller
@RequestMapping("/admin/partners")
public class AdminPartnersController {

    @Autowired
    private PartnerDao partnersDao;
    @Autowired
    private ImageControl imageControl;

    @GetMapping({"", "/"})
    public String getListPage(Model model) {
        model.addAttribute("partners", partnersDao.getList());
        return "admin.partners";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("title", "Новый партнер")
                .addAttribute("partner", new Partner())
                .addAttribute("callback", "add");
        return "admin.partner.edit";
    }

    @PostMapping("/add")
    public String add(Partner partner,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (Objects.nonNull(file)) {
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("partner", ext, imageControl.getDirectory("partners/"));

                if (imageControl.saveFile(tempFile, file)) {
                    partner.setFilename(tempFile.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminPartnersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        partnersDao.create(partner);
        return "redirect:/admin/partners";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("title", "Редактирование партнера")
                .addAttribute("callback", "edit");
        ofNullable(partnersDao.findById(Long.parseLong(id)))
                .ifPresent(partner -> model.addAttribute("partner", partner));
        return "admin.partner.edit";
    }

    @PostMapping("/edit")
    public String update(Partner partner,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (Objects.nonNull(file)) {
            imageControl.removeFile(partner.getFilename());
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("partner", ext, imageControl.getDirectory("partners/"));

                if (imageControl.saveFile(tempFile, file)) {
                    partner.setFilename(tempFile.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminPartnersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        partnersDao.update(partner);
        return "redirect:/admin/partners";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        ofNullable(partnersDao.findById(Long.parseLong(id)))
                .ifPresent(partner -> {
                    imageControl.removeFile(partner.getFilename());
                    partnersDao.delete(partner);
                });
        return "redirect:/admin/partners";
    }
}
