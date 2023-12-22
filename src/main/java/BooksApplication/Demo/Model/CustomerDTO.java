package BooksApplication.Demo.Model;

import BooksApplication.Demo.Domain.Book;
import BooksApplication.Demo.Domain.Role;

import lombok.Data;


import java.math.BigDecimal;
import java.util.Set;


@Data
public class CustomerDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private BigDecimal balance;
    private Role role;
    private Byte[] img;

    private Set<Book> books;

}
