package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.model.VerificationRequest;
import shop.model.CustomerUpdate;;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private VerificationRequest verificationRequest;
	@Autowired
	private CustomerUpdate customerControll;

    @RequestMapping(value = "/deletionmarkforall", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deletionMarkForAll(@RequestParam("key") String key){
        if (verificationRequest.verify(key)){
            return customerControll.delitionmarkforall();
        } else {
            return "Acces denied";
        } 

    }

    @RequestMapping(value = "/deletemarked", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteMarked(@RequestParam("key") String key){
        if (verificationRequest.verify(key)){
            return customerControll.deletemarked();
        } else {
            return "Acces denied";
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateCustomer(
        @RequestParam("key") String key,
        @RequestParam("ref") String ref,
        @RequestParam("name") String name,
        @RequestParam("in") String number,
        @RequestParam("pass") String pass){ 

        if (verificationRequest.verify(key)) {
            return customerControll.update(ref, name, number, pass);
        } else {
            return "Acces denied";
        }        

    }
}
