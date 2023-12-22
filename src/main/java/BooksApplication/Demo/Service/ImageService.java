package BooksApplication.Demo.Service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(Long customerId,MultipartFile file);

}
