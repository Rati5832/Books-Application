package BooksApplication.Demo.Controller;


import BooksApplication.Demo.Model.CustomerDTO;
import BooksApplication.Demo.Service.CustomerService;
import BooksApplication.Demo.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final CustomerService customerService;


    @GetMapping("/{id}/image")
    public String showUploadForm(@PathVariable Long id, Model model){

        model.addAttribute("custom", customerService.getCustomerById(id));

        return "imageuploadform";

    }

    @PostMapping("/{id}/image")
    public String HandleImagePost(@PathVariable Long id, @RequestParam("imagefile")MultipartFile multipartFile){

        imageService.saveImageFile(id, multipartFile);

        return "redirect:/mybooks";

    }


    @GetMapping("/{id}/customerimage")
    public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException{

        CustomerDTO customerDTO = customerService.getCustomerById(Long.valueOf(id));

        if (customerDTO.getImg() != null) {
            byte[] byteArray = new byte[customerDTO.getImg().length];
            int i = 0;

            for (Byte wrappedByte : customerDTO.getImg()){
                byteArray[i++] = wrappedByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
