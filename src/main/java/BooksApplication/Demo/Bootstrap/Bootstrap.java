package BooksApplication.Demo.Bootstrap;

import BooksApplication.Demo.Domain.Book;
import BooksApplication.Demo.Repository.BookRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    public BookRepository bookRepository;


    public Bootstrap(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        bookRepository.saveAll(getBooks());

    }


    public List<Book> getBooks(){


    List<Book> bookList = new ArrayList<>(5);


    Book book = new Book();

    book.setTitle("The Brothers Karamazov");
    book.setAuthor("Fyodor Dostoevsky");
    book.setCost(new BigDecimal("27.80"));

    bookList.add(book);

    Book book2 = new Book();

    book2.setTitle("Notes From Underground");
    book2.setAuthor("Fyodor Dostoevsky");
    book2.setCost(new BigDecimal("14.70"));

    bookList.add(book2);

    Book book3 = new Book();

    book3.setTitle("The Stranger");
    book3.setAuthor("Albert Camus");
    book3.setCost(new BigDecimal("17.30"));

    bookList.add(book3);

    Book book4 = new Book();

    book4.setTitle("The Metamorphosis");
    book4.setAuthor("Franz Kafka");
    book4.setCost(new BigDecimal("10.80"));

    bookList.add(book4);

    Book book5 = new Book();

    book5.setTitle("Divine Comedy");
    book5.setAuthor("Dante Alighieri");
    book5.setCost(new BigDecimal("16.95"));

    bookList.add(book5);

    return bookList;

    }

}
