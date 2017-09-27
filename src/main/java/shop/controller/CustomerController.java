package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import shop.entity.Customer;
import shop.model.VerificationRequest;
import shop.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    final private CustomerService customerService;
    @Autowired
    private VerificationRequest verificationRequest;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/deletionmarkforall", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String deletionMarkForAll(@RequestParam(value="key", required=false) String key) {
        if (verificationRequest.verify(key)){
            return customerService.delitionMarkForAll() ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        } 
    }

    @RequestMapping(value = "/deletemarked", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String deleteMarked(@RequestParam(value="key", required=false) String key) {
        if (verificationRequest.verify(key)){
            return customerService.deleteMarked() ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public String updateInfo(){
        return "GET not supported for update customer";       
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String update(@RequestParam(value="key", required=false) String key, @RequestBody Customer customer) { 
        if (verificationRequest.verify(key)) {
            return customerService.updateOrInsert(customer) ? customer + " is Ok" : "Fail";
        } else {
            return "Acces denied";
        }        
    }

    @RequestMapping(value = "/deletebyref", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String deleteByRef(@RequestParam(value="key", required=false) String key, @RequestParam(value="ref", required=true) String ref) { 
        if (verificationRequest.verify(key)) {
            return customerService.deleteByRef(ref) ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        }      
    }

    @RequestMapping(value = "/checkpass", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String checkPass(@RequestParam("in") String number, @RequestParam("pass") String pass) {
        return customerService.checkPass(number, pass) ? "Ok" : "Fail";
    }
}
