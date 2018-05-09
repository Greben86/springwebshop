package shop.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Customer> getList(@RequestParam(value = "key", required = false) String key) {
        if (verificationRequest.verify(key)) {
            return customerService.getList();
        } else {
            return null;
        }
    }

    @GetMapping("/update")
    public String updateInfo() {
        return "GET not supported for update customer";
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String update(@RequestParam(value = "key", required = false) String key,
            @RequestBody Customer customer) {
        if (verificationRequest.verify(key)) {
            return "Update customer " + customer + (customerService.updateOrInsert(customer) ? " is Ok" : " is Fail");
        } else {
            return "Acces denied";
        }
    }

    @GetMapping("/updatelist")
    public String updateListInfo() {
        return "GET not supported for update customer";
    }

    @PostMapping(value = "/updatelist", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String updateList(@RequestParam(value = "key", required = false) String key, @RequestBody List<Customer> list) {
        if (verificationRequest.verify(key)) {
            return "Uploaded " + list.size() + " customers " + (customerService.updateList(list) ? "succesful" : "unsuccesful");
        } else {
            return "Acces denied";
        }
    }

    @GetMapping(value = "/delete/{ref}", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String deleteById(@PathVariable(value = "ref") String ref,
            @RequestParam(value = "key", required = false) String key) {
        if (verificationRequest.verify(key)) {
            Customer customer = customerService.getByRef(ref);
            if (customerService.delete(customer)) {
                return "Delete customer " + customer + " is Ok";
            } else {
                return "Delete customer is Fail";
            }
        } else {
            return "Acces denied";
        }
    }

    @GetMapping(value = "/search", produces = MediaType.TEXT_PLAIN_VALUE)
    public Customer search(@RequestParam("login") String login, @RequestParam("pass") String pass) {
        return customerService.search(login, pass);
    }
    
    @GetMapping(value = "/checkpass", produces = MediaType.TEXT_PLAIN_VALUE)
    public String checkPass(@RequestParam("login") String login, @RequestParam("pass") String pass) {
        return customerService.checkPass(login, pass)?"Ok":"Fail";
    }

    @GetMapping(value = "/get/{ref}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer getCustomerById(@PathVariable("ref") String ref) {
        return customerService.getByRef(ref);
    }
}
