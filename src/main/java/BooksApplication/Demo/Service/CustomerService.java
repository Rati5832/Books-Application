package BooksApplication.Demo.Service;

import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Model.CustomerDTO;

import java.util.Set;

public interface CustomerService {


    Set<Customer> getCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO getCustomerByUsername(String username);


}
