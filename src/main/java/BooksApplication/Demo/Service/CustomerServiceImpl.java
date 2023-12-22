package BooksApplication.Demo.Service;

import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Mapper.CustomMapper;
import BooksApplication.Demo.Model.CustomerDTO;
import BooksApplication.Demo.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomMapper customerMapper;


    @Override
    public Set<Customer> getCustomers(){

        Set<Customer> customerSet = new HashSet<>();
        customerRepository.findAll().iterator().forEachRemaining(customerSet::add);

        return customerSet;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        Optional<Customer> customerOPT = customerRepository.findById(id);

        Customer customerOptional = customerOPT.get();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO = customerMapper.customerToCustomerDTO(customerOptional);

        return customerDTO;

    }


    @Override
    public CustomerDTO getCustomerByUsername(String email) {
        Optional<Customer> customerOPT = customerRepository.findByEmail(email);

        if (customerOPT.isPresent()) {
            Customer customerOptional = customerOPT.get();

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO = customerMapper.customerToCustomerDTO(customerOptional);

            return customerDTO;
        } else {

            throw new RuntimeException("Customer not found for email: " + email);
        }
    }
}
