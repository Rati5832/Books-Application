package BooksApplication.Demo.Config.Authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name cannot be blank")
    private String firstname;

    @NotBlank(message = "Last name cannot be blank")
    private String lastname;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")

    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}