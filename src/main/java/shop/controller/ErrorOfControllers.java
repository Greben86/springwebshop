package shop.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice("shop.controller")
public class ErrorOfControllers {
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exc) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Мы скоро все починим!");
        mav.addObject("exc", exc);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error.definition");
        return mav;
    }
}
