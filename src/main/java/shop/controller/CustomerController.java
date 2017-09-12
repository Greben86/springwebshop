package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "/deletionmarkforall", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deletionMarkForAll(@RequestParam("key") String key){
        if (verificationRequest.verify(key)){
            return customerService.delitionMarkForAll() ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        } 

    }

    @RequestMapping(value = "/deletemarked", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteMarked(@RequestParam("key") String key){
        if (verificationRequest.verify(key)){
            return customerService.deleteMarked() ? "Ok" : "Fail";
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
            Customer customer = new Customer();
            customer.setRef(ref);
            customer.setNumber(number);
            customer.setName(name);
            customer.setPass(pass);
            return customerService.updateOrInsert(customer) ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        }        

    }

    @RequestMapping(value = "/deletebyref", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteByRef(
        @RequestParam("key") String key, 
        @RequestParam("ref") String ref){ 

        if (verificationRequest.verify(key)) {
            return customerService.deleteByRef(ref) ? "Ok" : "Fail";
        } else {
            return "Acces denied";
        }        

    }

    @RequestMapping(value = "/checkpass", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String checkPass(@RequestParam("in") String number, @RequestParam("pass") String pass) {
        return customerService.checkPass(number, pass) ? "Ok" : "Fail";   
    }
}
