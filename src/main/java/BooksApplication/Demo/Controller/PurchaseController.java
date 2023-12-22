package BooksApplication.Demo.Controller;


import BooksApplication.Demo.Service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
@RequiredArgsConstructor
public class PurchaseController {


    private final PurchaseService purchaseService;


    @RequestMapping(value = "/purchase/{customerId}/{bookId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String purchaseBook(@PathVariable Long customerId, @PathVariable Long bookId, RedirectAttributes redirectAttributes) {

        try {
             purchaseService.purchaseBook(customerId, bookId);

             redirectAttributes.addFlashAttribute("purchaseSuccess", "Book Successfully Added To Your Collection!");



        } catch (Exception e) {

            String errorMessage = "Cant Buy A Book " + e.getMessage();
            redirectAttributes.addFlashAttribute("ErrorMessage",errorMessage);
        }

        return "redirect:/index";

    }

}

