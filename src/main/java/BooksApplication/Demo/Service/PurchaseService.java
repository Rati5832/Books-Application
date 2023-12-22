package BooksApplication.Demo.Service;


import BooksApplication.Demo.Domain.Book;
import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Exception.InsufficientBalanceException;
import BooksApplication.Demo.Repository.BookRepository;
import BooksApplication.Demo.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class PurchaseService {


    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;


    @Transactional
    public void purchaseBook(Long customerId, Long bookId){

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found With Given ID: " + customerId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book Not Found With Given ID:  " + bookId));


        BigDecimal bookPrice = book.getCost();
        BigDecimal newBalance = customer.getBalance().subtract(bookPrice);

        if(newBalance.compareTo(BigDecimal.ZERO) < 0){

            throw new InsufficientBalanceException("Insufficient Balance");

        }

        customer.setBalance(newBalance);
        customerRepository.save(customer);

        customer.getBooks().add(book);



    }


}
