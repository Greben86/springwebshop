package shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class HttpErrorController {
    
    @GetMapping("/400")
    public ModelAndView handle400() {
        return new ModelAndView("error.definition")
                .addObject("message", "400 - Bad Request");
    }
    
    @GetMapping("/403")
    public ModelAndView handle403() {
        return new ModelAndView("error.definition")
                .addObject("message", "403 - отказано в доступе");
    }
    
    @GetMapping("/404")
    public ModelAndView handle404() {
        return new ModelAndView("error.definition")
                .addObject("message", "404 - такой страницы не существует");
    }
    
    @GetMapping("/405")
    public ModelAndView handle405() {
        return new ModelAndView("error.definition")
                .addObject("message", "405 - Method Not Allowed");
    }
    
    @GetMapping("/406")
    public ModelAndView handle406() {
        return new ModelAndView("error.definition")
                .addObject("message", "406 - Not Acceptable");
    }
    
    @GetMapping("/408")
    public ModelAndView handle408() {
        return new ModelAndView("error.definition")
                .addObject("message", "408 - Request Time-out");
    }
    
    @GetMapping("/415")
    public ModelAndView handle415() {
        return new ModelAndView("error.definition")
                .addObject("message", "415 - Unsupported Media Type");
    }
    
    @GetMapping("/500")
    public ModelAndView handle500() {
        return new ModelAndView("error.definition")
                .addObject("message", "500 - Internal Server Error");
    }
    
    @GetMapping("/503")
    public ModelAndView handle503() {
        return new ModelAndView("error.definition")
                .addObject("message", "503 - Service Unavailable");
    }
    
}
