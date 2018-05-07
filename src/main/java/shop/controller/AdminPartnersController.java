package shop.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shop.dao.PartnerDao;
import shop.entity.Partner;
import shop.model.ImageControl;

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
        model.addAttribute("partner", new Partner());
        model.addAttribute("callback", "add");
        return "admin.partner.edit";
    }

    @PostMapping("/add")
    public String add(Partner partner,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("partner", ext, imageControl.getDirectory());

                InputStream is = file.getInputStream();
                if (imageControl.saveFile(tempFile, is)) {
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
        Partner partner = partnersDao.findById(Long.parseLong(id));
        model.addAttribute("partner", partner);
        model.addAttribute("callback", "edit");
        return "admin.partner.edit";
    }

    @PostMapping("/edit")
    public String update(Partner partner,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            imageControl.removeFile(partner.getFilename());
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("partner", ext, imageControl.getDirectory());

                InputStream is = file.getInputStream();
                if (imageControl.saveFile(tempFile, is)) {
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
        Partner partner = partnersDao.findById(Long.parseLong(id));
        if (partner != null) {
            imageControl.removeFile(partner.getFilename());
            partnersDao.delete(partner.getId());
        }
        return "redirect:/admin/partners";
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ResponseBody
    public byte[] getImage(@PathVariable("id") String id) {
        Partner partner = partnersDao.findById(Long.parseLong(id));
        if (partner != null) {
            return imageControl.readFile(partner.getFilename(), "noimagegood.png");
        } else {
            return null;
        }
    }
}