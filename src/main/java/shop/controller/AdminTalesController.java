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
import shop.dao.TaleDao;
import shop.entity.Tale;
import shop.model.ImageControl;


@Controller
@RequestMapping("/admin/tales")
public class AdminTalesController {
    
    @Autowired
    private TaleDao taleDao;
    @Autowired
    private ImageControl imageControl;

    @GetMapping({"", "/"})
    public String getListPage(Model model) {
        model.addAttribute("tales", taleDao.getList());
        return "admin.news";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("tale", new Tale());
        model.addAttribute("callback", "add");
        return "admin.tales.edit";
    }

    @PostMapping("/add")
    public String add(Tale tale,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("news", ext, imageControl.getDirectory());

                InputStream is = file.getInputStream();
                if (imageControl.saveFile(tempFile, is)) {
                    tale.setFilename(tempFile.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminNewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        taleDao.create(tale);
        return "redirect:/admin/tales";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") String id, Model model) {
        Tale tale = taleDao.findById(Long.parseLong(id));
        model.addAttribute("tale", tale);
        model.addAttribute("callback", "edit");
        return "admin.tales.edit";
    }

    @PostMapping("/edit")
    public String update(Tale tale,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            imageControl.removeFile(tale.getFilename());
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("news", ext, imageControl.getDirectory());

                InputStream is = file.getInputStream();
                if (imageControl.saveFile(tempFile, is)) {
                    tale.setFilename(tempFile.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminNewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        taleDao.update(tale);
        return "redirect:/admin/tales";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        Tale tale = taleDao.findById(Long.parseLong(id));
        if (tale != null) {
            imageControl.removeFile(tale.getFilename());
            taleDao.delete(tale.getId());
        }
        return "redirect:/admin/tales";
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ResponseBody
    public byte[] getImage(@PathVariable("id") String id) {
        Tale tale = taleDao.findById(Long.parseLong(id));
        if (tale != null) {
            return imageControl.readFile(tale.getFilename(), "noimagegood.png");
        } else {
            return null;
        }
    }
}
