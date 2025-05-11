package com.Payment_Reminder_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PaymentReminderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentReminderApplication.class, args);
	}

}
