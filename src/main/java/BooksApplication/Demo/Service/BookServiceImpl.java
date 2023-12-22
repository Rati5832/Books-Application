package BooksApplication.Demo.Service;

import BooksApplication.Demo.Domain.Book;
import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Repository.BookRepository;
import BooksApplication.Demo.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;


    @Override
    public Set<Book> getBooks() {

        Set<Book> bookSet = new HashSet<>();
        bookRepository.findAll().iterator().forEachRemaining(bookSet::add);

        return bookSet;


    }

    @Override
    public Set<Book> getUnpurchasedBooks(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found with given ID " + customerId));

        Set<Book> purchasedBooks = customer.getBooks();

        return bookRepository.findAll().stream()
                .filter(book -> !purchasedBooks.contains(book))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void deleteBookFromCustomer(Long customerId,Long bookId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found With Given ID: " + customerId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book Not Found With Given ID:  " + bookId));


       BigDecimal newBalance = customer.getBalance().add(book.getCost());

       customer.setBalance(newBalance);

       customer.getBooks().remove(book);
       book.getCustomers().remove(customer);

       customerRepository.save(customer);

    }


}
