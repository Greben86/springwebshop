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
import shop.entity.News;
import shop.model.ImageControl;
import shop.service.NewsService;
import static java.util.Optional.ofNullable;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private ImageControl imageControl;

    @GetMapping({"", "/"})
    public String getListPage(Model model) {
        model.addAttribute("news", newsService.getList());
        return "admin.news";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("title", "Новая новость");
        model.addAttribute("news", new News());
        model.addAttribute("callback", "add");
        return "admin.news.edit";
    }

    @PostMapping("/add")
    public String add(News news,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("news", ext, imageControl.getDirectory(""));

                if (imageControl.saveFile(tempFile, file)) {
                    news.setFilename(tempFile.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminNewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        newsService.create(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") String id, Model model) {
        News news = newsService.findById(Long.parseLong(id));
        model.addAttribute("title", "Редактирование новости");
        model.addAttribute("news", news);
        model.addAttribute("callback", "edit");
        return "admin.news.edit";
    }

    @PostMapping("/edit")
    public String update(News news,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            imageControl.removeFile(news.getFilename());
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = index != -1 ? file.getOriginalFilename().substring(index) : "";
                File tempFile = File.createTempFile("news", ext, imageControl.getDirectory(""));

                if (imageControl.saveFile(tempFile, file)) {
                    news.setFilename(tempFile.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminNewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        newsService.update(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        ofNullable(newsService.findById(Long.parseLong(id)))
                .ifPresent(news -> {
                    imageControl.removeFile(news.getFilename());
                    newsService.delete(news);
                });
        return "redirect:/admin/news";
    }

}
