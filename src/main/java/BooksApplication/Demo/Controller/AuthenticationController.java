package BooksApplication.Demo.Controller;


import BooksApplication.Demo.Config.Authentication.AuthenticationRequest;
import BooksApplication.Demo.Config.Authentication.AuthenticationResponse;
import BooksApplication.Demo.Config.Authentication.AuthenticationService;
import BooksApplication.Demo.Config.Authentication.RegisterRequest;
import BooksApplication.Demo.Domain.Book;
import BooksApplication.Demo.Service.BookService;
import BooksApplication.Demo.Service.CustomerService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final BookService bookService;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";

    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registerRequest") @Valid RegisterRequest registerRequest,
                               BindingResult bindingResult, Model model)  {

        if (bindingResult.hasErrors()) {

            return "register";

        }

        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

        if (authenticationResponse.isSuccess()) {
            model.addAttribute("succeed", true);

            Set<Book> books = bookService.getBooks();
            model.addAttribute("books", books);

            return "index";
        } else {
            model.addAttribute("succeed", false);
            model.addAttribute("errorMessage", authenticationResponse.getErrorMessage());
            return "register";
        }

    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute AuthenticationRequest authenticationRequest, Model model) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        if (authenticationResponse.getToken() != null) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                System.out.println("Authenticated user: " + username);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("User roles: {}", authentication.getAuthorities());

                return "redirect:/";
            } else {


                model.addAttribute("error", "Authentication object not populated correctly");
                return "login";
            }
        } else {
            model.addAttribute("error", authenticationResponse.getErrorMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logOut(){

        return "index";

    }

}