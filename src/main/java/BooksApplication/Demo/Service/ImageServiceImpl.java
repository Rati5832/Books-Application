package BooksApplication.Demo.Service;

import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {


    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void saveImageFile(Long customerId, MultipartFile file) {

        try{
            Customer customer = customerRepository.findById(customerId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for(byte b : file.getBytes()){

                byteObjects[i++] = b;

            }

            customer.setImg(byteObjects);
            customerRepository.save(customer);

        } catch(IOException e){

            log.error("Error ", e);
            e.printStackTrace();

        }
    }
}
