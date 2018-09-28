package shop.rest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("shop.rest")
public class ErrorOfRest {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleError(Exception exc) {
        return exc.getMessage();
    }
}
