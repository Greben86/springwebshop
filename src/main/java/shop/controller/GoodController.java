package shop.controller;

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

@RestController
@RequestMapping("/goods")
public class GoodController {
    @Autowired
    private VerificationRequest verificationRequest;

    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
	@ResponseBody
	public String uploadImg(@RequestBody MultipartFile file) {
 
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
}