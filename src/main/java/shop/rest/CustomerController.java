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
import shop.service.CustomerService;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    final private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Customer> getList() {
        return customerService.getList();
    }

    @GetMapping("/update")
    public String updateInfo() {
        return "GET not supported for update customer";
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
            produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String update(@RequestBody Customer customer) {
        return ofNullable(customer)
                .map(customerService::updateOrInsert)
                .map(result -> "Update customer " + (result ? " is Ok" : " is Fail"))
                .orElse("Update customer is Fail");
    }

    @GetMapping("/updatelist")
    public String updateListInfo() {
        return "GET not supported for update customer";
    }

    @PostMapping(value = "/updatelist", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
            produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String updateList(@RequestBody List<Customer> list) {
        return ofNullable(list)
                .map(customerService::updateList)
                .map(result -> "Uploaded customers " + (result ? "succesful" : "unsuccesful"))
                .orElse("Customers is not uploaded");
    }

    @GetMapping(value = "/delete/{ref}", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String deleteById(@PathVariable(value = "ref") String ref) {
        return ofNullable(customerService.getByRef(ref))
                .map(customerService::delete)
                .map(result -> "Delete customer " + (result ? "is Ok" : "is Fail"))
                .orElse("Delete customer is Fail");
    }

    @GetMapping(value = "/search", produces = MediaType.TEXT_PLAIN_VALUE)
    public Customer search(@RequestParam("login") String login, 
            @RequestParam("pass") String pass) {
        return customerService.search(login, pass);
    }
    
    @GetMapping(value = "/checkpass", produces = MediaType.TEXT_PLAIN_VALUE)
    public String checkPass(@RequestParam("login") String login, 
            @RequestParam("pass") String pass) {
        return customerService.checkPass(login, pass)?"Ok":"Fail";
    }

    @GetMapping(value = "/get/{ref}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer getCustomerById(@PathVariable("ref") String ref) {
        return customerService.getByRef(ref);
    }
}
