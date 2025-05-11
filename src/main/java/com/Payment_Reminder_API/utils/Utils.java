package com.Payment_Reminder_API.utils;

import com.Payment_Reminder_API.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object pricipal = authentication.getPrincipal();
        User user = (User) pricipal;
        return user;
    }
}
