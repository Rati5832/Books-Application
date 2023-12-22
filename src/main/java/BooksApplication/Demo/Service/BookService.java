package BooksApplication.Demo.Service;

import BooksApplication.Demo.Domain.Book;

import java.util.Set;

public interface BookService {

    Set<Book> getBooks();

    Set<Book> getUnpurchasedBooks(Long customerId);
    void deleteBookFromCustomer(Long CustomerId,Long BookId);
}
