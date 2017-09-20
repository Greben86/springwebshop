package shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import shop.entity.Good;
import shop.model.VerificationRequest;
import shop.service.GoodService; 

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/goods")
public class GoodController {
	final private GoodService goodService;
    @Autowired
	private VerificationRequest verificationRequest;
	
	@Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

	@RequestMapping(value="/upload", method=RequestMethod.GET)
	@ResponseBody
    public String provideUploadInfo() {
        return "Вы можете загружать файл с использованием того же URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadImg(@RequestParam("file") MultipartFile file) {
 
		return "uploadimg: " + file;
	}

    // @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	// @ResponseBody
	// public String uploadImg(MultipartFile file) {
 
	// 	String name = null;
 
	// 	if (!file.isEmpty()) {
	// 		try {
	// 			byte[] bytes = file.getBytes();
 
	// 			name = file.getOriginalFilename();
 
	// 			return "You successfully uploaded file=" + name;
	// 		} catch (Exception e) {
	// 			return "You failed to upload " + name + " => " + e.getMessage();
	// 		}
	// 	} else {
	// 		return "You failed to upload " + name + " because the file was empty.";
	// 	}
	// }

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