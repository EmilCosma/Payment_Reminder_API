package com.Payment_Reminder_API.controllers;

import com.Payment_Reminder_API.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {

    //private RegistrationService registrationService;
    private UserService userService;

    /**
     * Get personal information
     * @return
     */
    @GetMapping("/user-info")
    public String getAllUsersInfo(){
        return userService.getAllUsersInfo();
    }

}
