package BooksApplication.Demo.Mapper;

import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Model.CustomerDTO;
import org.springframework.stereotype.Component;


@Component
public class CustomMapperImpl implements CustomMapper {

    @Override
    public CustomerDTO customerToCustomerDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();

        if (customer.getId() != null) {
            customerDTO.setId(customer.getId());
        }
        customerDTO.setFirstname(customer.getFirstname());
        customerDTO.setLastname(customer.getLastname());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setBalance(customer.getBalance());
        customerDTO.setRole(customer.getRole());
        customerDTO.setImg(customer.getImg());
        customerDTO.setBooks(customer.getBooks());

        return customerDTO;
    }

    @Override
    public Customer customerDTOToCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id(customerDTO.getId());
        customer.firstname(customerDTO.getFirstname());
        customer.lastname(customerDTO.getLastname());
        customer.email(customerDTO.getEmail());
        customer.balance(customerDTO.getBalance());
        customer.role(customerDTO.getRole());
        customer.img(customerDTO.getImg());
        customer.books(customerDTO.getBooks());

        return customer.build();
    }
}