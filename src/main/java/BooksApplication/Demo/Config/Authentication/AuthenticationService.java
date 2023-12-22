package BooksApplication.Demo.Config.Authentication;

import BooksApplication.Demo.Config.Jwt.JwtService;
import BooksApplication.Demo.Domain.Customer;
import BooksApplication.Demo.Domain.Role;
import BooksApplication.Demo.Domain.Token;
import BooksApplication.Demo.Domain.TokenType;
import BooksApplication.Demo.Repository.CustomerRepository;
import BooksApplication.Demo.Repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Optional;



@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    private void saveUserToken(Customer customer, String jwtToken) {
        var token = Token.builder()
                .customer(customer)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }




    public AuthenticationResponse register(RegisterRequest request) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(request.getEmail());

        if (existingCustomer.isEmpty()) {

            Customer newCustomer = Customer.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .balance(new BigDecimal("80"))
                    .build();


            Customer savedCustomer = customerRepository.save(newCustomer);


            String jwtToken = jwtService.generateToken(savedCustomer);
            saveUserToken(savedCustomer, jwtToken);


            if (jwtToken != null && !jwtToken.isEmpty()) {
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .success(true)
                        .build();
            } else {
                // Token is null or empty, registration failed
                return AuthenticationResponse.builder()
                        .token(null)
                        .success(false)
                        .errorMessage("Registration failed. Please check your input.")
                        .build();
            }
        } else {

            return AuthenticationResponse.builder()
                    .token(null)
                    .success(false)
                    .errorMessage("Email is already in use.")
                    .build();
        }
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            var user = customerRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            // Authentication failed
            return AuthenticationResponse.builder()
                    .errorMessage("Invalid username or password")
                    .build();
        }
    }


    private void revokeAllUserTokens(Customer customer) {
        var validUserTokens = tokenRepository.findAllValidTokenByCustomer(customer);
        if (validUserTokens.isEmpty()) {
            return;
        }

        // Logging
        log.info("Revoking tokens for customer with ID: {}", customer.getId());

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);

        // Logging
        log.info("Revoked {} tokens for customer with ID: {}", validUserTokens.size(), customer.getId());
    }
}
