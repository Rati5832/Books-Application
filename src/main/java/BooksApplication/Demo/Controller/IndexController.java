package BooksApplication.Demo.Controller;

import BooksApplication.Demo.Domain.Book;
import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Service.BookService;
import BooksApplication.Demo.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BookService bookService;
    private final CustomerService customerService;



    @RequestMapping({"/index", "/", ""})
    public String getIndexPage(Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!"anonymousUser".equals(authentication.getPrincipal())) {
            Customer customer = (Customer) authentication.getPrincipal();
            model.addAttribute("customerId", customer.getId());
            model.addAttribute("auth", authentication);

            Set<Book> unpurchasedBooks = bookService.getUnpurchasedBooks(customer.getId());
            model.addAttribute("books", unpurchasedBooks);

        } else {

            Set<Book> books = bookService.getBooks();
            model.addAttribute("books", books);

        }

        model.mergeAttributes(redirectAttributes.getFlashAttributes());

        return "index";
    }

    @GetMapping("/mybooks")
    public String myBooks(Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        model.addAttribute("customer", customerService.getCustomerByUsername(email));

        Set<Book> books = bookService.getBooks();
        model.addAttribute("books", books);

        return "mybooks";
    }

    @GetMapping({"/delete/{customerId}/{bookId}"})
    public String deleteBookById(@PathVariable Long customerId, @PathVariable Long bookId){

        bookService.deleteBookFromCustomer(customerId,bookId);

        return "redirect:/mybooks";

    }


}