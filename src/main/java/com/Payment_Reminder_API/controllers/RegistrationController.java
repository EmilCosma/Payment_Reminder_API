package com.Payment_Reminder_API.controllers;

import com.Payment_Reminder_API.models.requests.AuthenticationResponse;
import com.Payment_Reminder_API.models.requests.LoginRequest;
import com.Payment_Reminder_API.models.requests.RegistrationRequest;
import com.Payment_Reminder_API.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Class is a controller that contains endpoints for
 * operations for registration
 */
@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    /**
     * SignUp
     * @param request
     * @return
     */
    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    /**
     * Login
     * @param request
     * @return
     */
    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody LoginRequest request){
        return registrationService.authenticate(request);
    }



}
