package com.Payment_Reminder_API.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderRequest {
    private String email;
    private String description;
    private Integer amount;
}
