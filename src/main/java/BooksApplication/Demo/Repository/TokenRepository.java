package BooksApplication.Demo.Repository;

import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    List<Token> findAllValidTokenByCustomer(Customer customer);

    Optional<Token> findByToken(String token);
}