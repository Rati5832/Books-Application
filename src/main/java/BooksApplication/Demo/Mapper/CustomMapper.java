package BooksApplication.Demo.Mapper;


import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomMapper {

    CustomMapper INSTANCE = Mappers.getMapper(CustomMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);


}
