package shop.rest;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.entity.Good;
import shop.model.VerificationRequest;
import shop.model.ImageControl;
import shop.service.GoodService;

@RestController
@RequestMapping("/goods")
public class GoodController {

    final private GoodService goodService;
    @Autowired
    private VerificationRequest verificationRequest;
    @Autowired
    private ImageControl imageControl;

    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @GetMapping(value = "/uploadimg", produces = MediaType.TEXT_PLAIN_VALUE)
    public String provideUploadInfo() {
        return "GET not supported for upload image";
    }

    @PostMapping(value = "/uploadimg/{id}", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String uploadImg(@PathVariable("id") String id,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (!verificationRequest.verify(key)) {
            return "Acces denied";
        }
        return Optional.ofNullable(goodService.getById(Long.parseLong(id)))
                .map(good -> imageControl.saveFile(good.getFilename(), file))
                .map(result -> "Upload file for good " + id + (result ? " is success" : " is fail"))
                .orElse("Save image is fail, good (" + id + ") not exist");
    }

    @GetMapping(value = "/clearimg/{id}", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String clearImg(@PathVariable("id") String id,
            @RequestParam(value = "key", required = false) String key) {
        if (!verificationRequest.verify(key)) {
            return "Acces denied";
        }
        return Optional.ofNullable(goodService.getById(Long.parseLong(id)))
                .map(good -> good.getFilename())
                .map(imageControl::removeFile)
                .map(result -> "Clear image " + (result ? " is Ok" : " is Fail"))
                .orElse("Clear image is fail, good (" + id + ") is not exist");
    }

    @GetMapping(value = "/update")
    public String updateInfo() {
        return "GET not supported for update good";
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String update(@RequestBody Good good,
            @RequestParam(value = "key", required = false) String key) {
        if (!verificationRequest.verify(key)) {
            return "Acces denied";
        }
        return Optional.ofNullable(good)
                .map(goodService::updateOrInsert)
                .map(result -> "Update good " + (result ? " is Ok" : " is Fail"))
                .orElse("Update good is fail");
    }

    @GetMapping(value = "/updatelist")
    public String updateListInfo() {
        return "GET not supported for update goods";
    }

    @PostMapping(value = "/updatelist", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String updateList(@RequestBody List<Good> list,
            @RequestParam(value = "key", required = false) String key) {
        if (!verificationRequest.verify(key)) {
            return "Acces denied";
        }
        return Optional.ofNullable(list)
                .map(goodService::updateList)
                .map(result -> "Uploaded goods " + (result ? "succesful" : "unsuccesful"))
                .orElse("Uploaded goods fail");
    }

    @GetMapping(value = "/delete/{id}", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String deleteById(@PathVariable("id") String id,
            @RequestParam(value = "key", required = false) String key) {
        if (!verificationRequest.verify(key)) {
            return "Acces denied";
        }
        return Optional.ofNullable(goodService.getById(Long.parseLong(id)))
                .map(good -> {
                    goodService.delete(good);
                    imageControl.removeFile(good.getFilename());
                    return "Delete good " + good + " is Ok; ";
                })
                .orElse("Delete good is Fail");
    }

    @GetMapping(value = "/folders/{owner}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Good> getFolders(@PathVariable("owner") String owner) {
        return goodService.getFolders(Long.parseLong(owner));
    }

    @GetMapping(value = "/list/{owner}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Good> getList(@PathVariable("owner") String owner) {
        return goodService.getList(Long.parseLong(owner));
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Good getGood(@PathVariable("id") String id) {
        return goodService.getById(Long.parseLong(id));
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE,
        MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable("id") String id) {
        return Optional.ofNullable(goodService.getById(Long.parseLong(id)))
                .map(good -> imageControl.readFile(good.getFilename(),
                        good.getFolder() ? "noimagefolder.png" : "noimagegood.png"))
                .orElse(new byte[0]);
    }
}
