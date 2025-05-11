package com.Payment_Reminder_API.services;

import com.Payment_Reminder_API.models.User;
import com.Payment_Reminder_API.models.requests.AuthenticationResponse;
import com.Payment_Reminder_API.models.requests.LoginRequest;
import com.Payment_Reminder_API.models.requests.RegistrationRequest;
import com.Payment_Reminder_API.repositories.UserRepository;
import com.Payment_Reminder_API.services.regex.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used for Register Logic
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Signing in a new user
     *
     * @param request
     * @return
     */
    public AuthenticationResponse register(RegistrationRequest request) {
        //se verifica daca emailul e valid
        if (!isEmailValid(request.getEmail())) {
            throw new RuntimeException("Email is not valid");
        }

        User user = authenticateUser(request);
        var checkUser = userRepository.findByEmail(request.getEmail());


        if (checkUser.isPresent()) {
            throw new RuntimeException("User already exist");
        }
        var jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * Regex for email validation
     *
     * @param email
     * @return
     */
    public boolean isEmailValid(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    /**
     * Creates a user with builder for loging in
     *
     * @return
     */
    private User authenticateUser(RegistrationRequest request) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .reminders(new ArrayList<>())
                .build();
    }

    /**
     * Se face login
     *
     * @param request
     * @return
     */
    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * This method allows the user to delete it's account permanently
     *
     * @return
     */
    public String deletePersonalAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {

            User userDetails = (User) principal;

            String username = userDetails.getUsername();
            var user = userRepository.findByEmail(username);
            if (user.isPresent()) {
                userRepository.delete(user.get());
                return "User deleted";
            }
        }

        SecurityContextHolder.clearContext();
        return "User does not exist";
    }
}