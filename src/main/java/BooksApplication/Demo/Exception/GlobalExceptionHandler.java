package BooksApplication.Demo.Exception;

import BooksApplication.Demo.Exception.InsufficientBalanceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public String handleInsufficientBalanceException(InsufficientBalanceException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }



    @ExceptionHandler(Exception.class)
    public String handleOtherExceptions(Exception ex, Model model) {
        model.addAttribute("error", "An unexpected error occurred");
        return "error";
    }
}