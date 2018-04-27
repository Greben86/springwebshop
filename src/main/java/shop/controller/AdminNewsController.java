package shop.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import shop.dao.NewsDao;
import shop.entity.News;
import shop.model.ImageControl;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Autowired
    private NewsDao newsDao;
    @Autowired
    private ImageControl imageControl;

    @GetMapping({"", "/"})
    public String getPageNews(Model model) {
        model.addAttribute("news", newsDao.getList());
        return "admin.news";
    }

    @GetMapping("/add")
    public String getPageAddNews(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("callback", "add");
        return "admin.news.edit";
    }

    @PostMapping("/add")
    public String addNews(News news,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            try {
                InputStream is = file.getInputStream();
                if (imageControl.saveFile(file.getName(), is)) {
                    news.setFilename(file.getOriginalFilename());
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminNewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        newsDao.create(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/edit/{id}")
    public String getPageEditNews(@PathVariable("id") String id, Model model) {
        News news = newsDao.findById(Long.parseLong(id));
        model.addAttribute("news", news);
        model.addAttribute("callback", "edit");
        return "admin.news.edit";
    }

    @PostMapping("/edit")
    public String updateNews(News news) {
        newsDao.update(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable String id) {
        newsDao.delete(Long.parseLong(id));
        return "redirect:/admin/news";
    }

}
