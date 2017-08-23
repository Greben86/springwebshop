package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import shop.model.VerificationRequest;
import shop.model.CustomerControll;

@Controller
public class AppController {
    @Autowired
    private VerificationRequest verificationRequest;
	@Autowired
	private CustomerControll customerControll;

    @RequestMapping(value = "/deletionmarkforall", method = RequestMethod.GET)
    public ModelAndView deletionMarkForAll(@RequestParam("key") String key){
        ModelAndView modelAndView = new ModelAndView("customer");
        if (verificationRequest.verify(key)){
            modelAndView.addObject("operation", "Deletion mark for all customers");
            modelAndView.addObject("result", customerControll.delitionmarkforall());
        } else {
            modelAndView.setViewName("error");
            modelAndView.addObject("message", "Acces denied");
        }        
        return modelAndView;
    }

    @RequestMapping(value = "/deletemarked", method = RequestMethod.GET)
    public ModelAndView deleteMarked(@RequestParam("key") String key){
        ModelAndView modelAndView = new ModelAndView("customer");
        if (verificationRequest.verify(key)){
            modelAndView.addObject("operation", "Delete marked customers");
            modelAndView.addObject("result", customerControll.deletemarked());
        } else {
            modelAndView.setViewName("error");
            modelAndView.addObject("message", "Acces denied");
        }        
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateCustomer(
        @RequestParam("key") String key,
        @RequestParam("ref") String ref,
        @RequestParam("name") String name,
        @RequestParam("in") String number,
        @RequestParam("pass") String pass){
        ModelAndView modelAndView = new ModelAndView("customer");
        if (verificationRequest.verify(key)) {
            modelAndView.addObject("operation", "Update customer " + name);
            modelAndView.addObject("result", customerControll.update(ref, name, number, pass));
        } else {
            modelAndView.setViewName("error");
            modelAndView.addObject("message", "Acces denied");
        }        
    	return modelAndView;
    }
}
