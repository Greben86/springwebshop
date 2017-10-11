package shop.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import shop.entity.Good;
import shop.model.VerificationRequest;
import shop.model.SaveFile;
import shop.service.GoodService; 

@RestController
@RequestMapping("/goods")
public class GoodController {
	final private GoodService goodService;
    @Autowired
    private VerificationRequest verificationRequest;
    @Autowired
    private SaveFile saveFile;
	
	@Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @RequestMapping(value="/uploadimg", method=RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String provideUploadInfo() {
        return "Not supported GET method";
    }
    
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	@ResponseBody
    public String uploadImg(@RequestParam(value="key", required=false) String key, 
                            @RequestParam(value="file", required=false) MultipartFile file) {
        if (verificationRequest.verify(key)){
            try(InputStream is = file.getInputStream();) {
                return saveFile.save(file.getOriginalFilename(), is);
            } catch (IOException e) {
                return e.getMessage();
            }
        } else {
            return "Acces denied";
        }
	}

	@RequestMapping(value = "/deletionmarkforall", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String deletionMarkForAll(@RequestParam(value="key", required=false) String key){
        if (verificationRequest.verify(key)){
            return goodService.delitionMarkForAll() ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        } 
    }

    @RequestMapping(value = "/deletemarked", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String deleteMarked(@RequestParam(value="key", required=false) String key){
        if (verificationRequest.verify(key)){
            return goodService.deleteMarked() ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        }
    }

	@RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public String updateInfo(){
        return "GET not supported for update good";       
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String update(@RequestParam(value="key", required=false) String key, @RequestBody Good good){ 
        if (verificationRequest.verify(key)) {
            return goodService.updateOrInsert(good) ? good + " is Ok" : "Fail";
        } else {
            return "Acces denied";
        }        
    }

    @RequestMapping(value = "/deletebyref", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String deleteByRef(@RequestParam(value="key", required=false) String key, @RequestParam(value="ref", required=true) String ref){ 
        if (verificationRequest.verify(key)) {
            return goodService.deleteByRef(ref) ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        }      
    }

    @RequestMapping(value = "/folders/{owner}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<Good> getFolder(@PathVariable(value = "owner") String owner) {
        return goodService.getFolders(Long.parseLong(owner));
    }

    @RequestMapping(value = "/list/{owner}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<Good> getCatalog(@PathVariable(value = "owner") String owner) {
        return goodService.getList(Long.parseLong(owner));
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Good getGood(@PathVariable(value = "id") String id) {
        return goodService.getById(Long.parseLong(id));
    }
}