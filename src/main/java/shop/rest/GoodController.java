package shop.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
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

    @RequestMapping(value="/uploadimg", method=RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String provideUploadInfo() {
        return "GET not supported for upload image";
    }
    
    @RequestMapping(value = "/uploadimg/{id}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    public String uploadImg(@PathVariable("id") String id,
                            @RequestParam(value="key", required=false) String key, 
                            @RequestParam(value="file", required=false) MultipartFile file) {
        if (verificationRequest.verify(key)){
            Good good = goodService.getById(Long.parseLong(id));
            if (good!=null) {
                try(InputStream is = file.getInputStream();) {
                    return "You " + (imageControl.saveFile(good.getFilename(), is) ? "successfully" : "failed") + " uploaded file=" + good.getFilename();
                } catch (IOException e) {
                    return e.getMessage();
                }
            } else {
                return "Save image is fail, good ("+id+") not exist";
            }            
        } else {
            return "Acces denied";
        }
    }

    @RequestMapping(value = "/clearimg/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    public String clearImg(@PathVariable("id") String id,
                           @RequestParam(value="key", required=false) String key) {
        if (verificationRequest.verify(key)){
            Good good = goodService.getById(Long.parseLong(id));
            if (good!=null) {
                return "Clear image "+good.getFilename()+(imageControl.removeFile(good.getFilename())?" is Ok":" is Fail");
            } else {
                return "Clear image is fail, good ("+id+") not exist";
            }            
        } else {
            return "Acces denied";
        }
    }

	@RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateInfo(){
        return "GET not supported for update good";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    public String update(@RequestParam(value="key", required=false) String key, @RequestBody Good good){
        if (verificationRequest.verify(key)) {
            return "Update good "+good+(goodService.updateOrInsert(good)?" is Ok":" is Fail");
        } else {
            return "Acces denied";
        }
    }

    @RequestMapping(value = "/updatelist", method = RequestMethod.GET)
    public String updateListInfo() {
        return "GET not supported for update goods";
    }

    @RequestMapping(value = "/updatelist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String updateList(@RequestParam(value="key", required=false) String key, @RequestBody List<Good> list){
        if (verificationRequest.verify(key)) {
            return "Uploaded "+list.size()+" goods "+(goodService.updateList(list)?"succesful":"unsuccesful");
        } else {
            return "Acces denied";
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    public String deleteById(@PathVariable("id") String id,
                              @RequestParam(value="key", required=false) String key){ 
        if (verificationRequest.verify(key)) {
            Good good = goodService.getById(Long.parseLong(id));
            if (goodService.delete(good)) {                
                return "Delete good "+good+" is Ok; "+imageControl.removeFile(good.getFilename());
            } else {
                return "Delete good is Fail";
            }
        } else {
            return "Acces denied";
        }
    }

    @RequestMapping(value = "/folders/{owner}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Good> getFolders(@PathVariable("owner") String owner) {
        return goodService.getFolders(Long.parseLong(owner));
    }

    @RequestMapping(value = "/list/{owner}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Good> getList(@PathVariable("owner") String owner) {
        return goodService.getList(Long.parseLong(owner));
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Good getGood(@PathVariable("id") String id) {
        return goodService.getById(Long.parseLong(id));
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable("id") String id) {
        Good good = goodService.getById(Long.parseLong(id));
        if (good!=null) {
            return imageControl.readFile(good.getFilename(), good.getFolder() ? "noimagefolder.png" : "noimagegood.png");
        } else {
            return null;
        }        
    }
}