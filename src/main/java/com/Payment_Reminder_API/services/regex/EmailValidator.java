package com.Payment_Reminder_API.services.regex;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //todo: Regex to validate email
        return true;
    }
}