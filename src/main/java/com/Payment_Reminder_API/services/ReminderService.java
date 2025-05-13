package com.Payment_Reminder_API.services;

import com.Payment_Reminder_API.models.Reminder;
import com.Payment_Reminder_API.models.User;
import com.Payment_Reminder_API.models.requests.ReminderRequest;
import com.Payment_Reminder_API.repositories.ReminderRepository;
import com.Payment_Reminder_API.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final EmailService emailService;


    public void addReminder(ReminderRequest reminderRequest) {

        // ia userul din token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        //creaza si populeaza un entety de tip Reminder
        Reminder reminder= new Reminder();
        reminder.setEmail(reminderRequest.getEmail());
        reminder.setDescription(reminderRequest.getDescription());
        reminder.setAmount(reminderRequest.getAmount());
        reminder.setUser(user);

        // valori default
        reminder.setIsPaid(false);
        reminder.setCreatedAt(LocalDateTime.now());
        reminder.setRemindersSent(0);

        reminder.setUser(user);

        //user.getReminders().add(reminder);

        // Then send the initial reminder email
        sendReminderEmail(reminder);

        reminder.setRemindersSent(1);
        reminderRepository.save(reminder);

    }
    /**
     * Scheduled method to send reminder emails every week.
     */
    @Scheduled(cron = "0 0 12 * * 0") // Weekly on Sunday at 12:00 PM (noon)
    public void sendWeeklyReminders() {
        List<Reminder> unpaidReminders = reminderRepository.findByIsPaidFalse();

        for (Reminder reminder : unpaidReminders) {
            sendReminderEmail(reminder);
            reminder.setRemindersSent(reminder.getRemindersSent() + 1);
            reminderRepository.save(reminder);
        }
    }

    private void sendReminderEmail(Reminder reminder) {
        String subject = "Payment Reminder - Action Required";

        // Create professional HTML email template
        String htmlBody = createHtmlEmailTemplate(reminder);

        // Send the email as HTML
        emailService.sendHtmlEmail(reminder.getEmail(), subject, htmlBody);
    }

    private String createHtmlEmailTemplate(Reminder reminder) {
        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Payment Reminder</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    line-height: 1.6;
                    color: #333333;
                    margin: 0;
                    padding: 0;
                }
                .container {
                    max-width: 600px;
                    margin: 0 auto;
                    padding: 20px;
                }
                .header {
                    background-color: #0056b3;
                    color: white;
                    padding: 20px;
                    text-align: center;
                }
                .content {
                    padding: 20px;
                    background-color: #f9f9f9;
                    border: 1px solid #dddddd;
                }
                .amount {
                    font-size: 22px;
                    font-weight: bold;
                    color: #0056b3;
                }
                .footer {
                    margin-top: 20px;
                    font-size: 12px;
                    color: #777777;
                    text-align: center;
                }
                .warning {
                    margin-top: 20px;
                    padding: 10px;
                    background-color: #fff3cd;
                    border-left: 4px solid #ffc107;
                    color: #856404;
                }
                .contact-info {
                    margin-top: 20px;
                    padding: 15px;
                    background-color: #e9f7fe;
                    border: 1px solid #bee5eb;
                    border-radius: 4px;
                }
                .contact-info p {
                    margin: 5px 0;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>Payment Reminder</h1>
                </div>
                <div class="content">
                    <p>Hello,</p>
                    <p>This is a reminder that you have an outstanding payment for:</p>
                    <p><strong>%s</strong></p>
                    <p>Amount Due: <span class="amount">%d RON</span></p>
                    
                    <div class="warning">
                        <p><strong>⚠️ Important Notice:</strong></p>
                        <p>This is an automated reminder. If this payment remains unpaid, you will receive additional 
                        reminders with increasing frequency.</p>
                        <p>This is reminder #%d for this payment.</p>
                    </div>
                    
                    <div class="contact-info">
                        <p><strong>This reminder was sent by: %s</strong></p>
                        <p>If you have any questions or would like to arrange payment, please contact:</p>
                        <p><a href="mailto:%s">%s</a></p>
                    </div>
                    
                    <p>If you have already made this payment, please disregard this email.</p>
                </div>
                <div class="footer">
                    <p>This is an automated message. Please do not reply directly to this email.</p>
                    <p>© %d Payment Reminder System. All rights reserved.</p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(
                reminder.getDescription(),
                reminder.getAmount(),
                reminder.getRemindersSent() + 1,
                reminder.getUser().getName(), // Add user's name
                reminder.getUser().getEmail(), // Add user's email as both mailto link and display text
                reminder.getUser().getEmail(),
                LocalDateTime.now().getYear()
        );
    }



}
