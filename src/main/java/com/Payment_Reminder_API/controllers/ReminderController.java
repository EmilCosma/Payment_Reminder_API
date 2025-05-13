package com.Payment_Reminder_API.controllers;

import com.Payment_Reminder_API.models.Reminder;
import com.Payment_Reminder_API.models.requests.ReminderRequest;
import com.Payment_Reminder_API.services.ReminderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/reminder")
@AllArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping("/add")
    public ResponseEntity<String> addReminder(@RequestBody ReminderRequest reminderRequest) {
        reminderService.addReminder(reminderRequest);
        return ResponseEntity.ok("Reminder added successfully");
    }
}
