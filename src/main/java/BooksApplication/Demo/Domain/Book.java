package BooksApplication.Demo.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private BigDecimal cost;

    @ManyToMany(mappedBy = "books")
    private Set<Customer> customers = new HashSet<>();

}
