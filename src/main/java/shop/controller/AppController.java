package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import shop.model.AddCustomer;

@Controller
public class AppController {
	@Autowired
	private AddCustomer addCustomer;

    @RequestMapping("/")
    public String hello(Model model){
        model.addAttribute("hello", "Hello my first Spring App!");
        return "hello";
    }

    @RequestMapping("/add")
    public String add(Model model){
    	model.addAttribute("result", addCustomer.add());
    	return "addcustomer";
    }
}
