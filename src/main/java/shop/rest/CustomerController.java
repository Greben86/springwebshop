package shop.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Customer> getList(@RequestParam(value = "key", required = false) String key) {
        if (verificationRequest.verify(key)) {
            return customerService.getList();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateInfo() {
        return "GET not supported for update customer";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String update(@RequestParam(value = "key", required = false) String key,
            @RequestBody Customer customer) {
        if (verificationRequest.verify(key)) {
            return "Update customer " + customer + (customerService.updateOrInsert(customer) ? " is Ok" : " is Fail");
        } else {
            return "Acces denied";
        }
    }

    @RequestMapping(value = "/updatelist", method = RequestMethod.GET)
    public String updateListInfo() {
        return "GET not supported for update customer";
    }

    @RequestMapping(value = "/updatelist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String updateList(@RequestParam(value = "key", required = false) String key, @RequestBody List<Customer> list) {
        if (verificationRequest.verify(key)) {
            return "Uploaded " + list.size() + " customers " + (customerService.updateList(list) ? "succesful" : "unsuccesful");
        } else {
            return "Acces denied";
        }
    }

    @RequestMapping(value = "/delete/{ref}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
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

    @RequestMapping(value = "/checkpass", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String checkPass(@RequestParam("login") String login, @RequestParam("pass") String pass) {
        return customerService.checkPass(login, pass) ? "Ok" : "Fail";
    }

    @RequestMapping(value = "/get/{ref}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer getCustomerById(@PathVariable("ref") String ref) {
        return customerService.getByRef(ref);
    }
}
