package shop.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value="/upload", method=RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String provideUploadInfo() {
        return "Not supported GET method";
    }
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	@ResponseBody
	public String uploadImg(@RequestParam(value="file", required=false) MultipartFile file) {
        try(InputStream is = file.getInputStream();) {
            return saveFile.save(file.getOriginalFilename(), is);
        } catch (IOException e) {
            return e.getMessage();
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
}