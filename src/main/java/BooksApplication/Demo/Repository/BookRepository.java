package BooksApplication.Demo.Repository;

import BooksApplication.Demo.Domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
