# Payment Reminder API
< IN PROGRESS >
## Overview
The Payment Reminder API is a Spring Boot application designed to manage and automate payment reminders. It provides functionality to create reminders, track unpaid payments, and send automated email notifications with increasing frequency to recipients.
## Features
- **Reminder Management**: Create, read, update, and delete payment reminders
- **Automated Email Notifications**: Schedule and send professional HTML emails to remind users of outstanding payments
- **Escalating Reminder System**: Automatically increases the frequency of reminders for unpaid payments
- **User Management**: Associate reminders with users who created them

## Technologies
- Java 23
- Spring Boot
- Spring Data JPA
- Jakarta EE
- Lombok
- Spring Mail for email services
- HTML/CSS for email templates

## Project Structure
``` 
Payment_Reminder_API/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── Payment_Reminder_API/
│   │   │           ├── controllers/        # REST endpoints
│   │   │           ├── models/             # Entity classes
│   │   │           ├── repositories/       # Data access layer
│   │   │           ├── services/           # Business logic
│   │   │           │   ├── EmailService.java     # Email sending service
│   │   │           │   └── ReminderService.java  # Reminder management service
│   │   │           └── PaymentReminderApiApplication.java  # Main application class
│   │   └── resources/
│   │       ├── application.properties      # Application configuration
│   │       ├── static/                     # Static resources
│   │       └── templates/                  # View templates (if any)
│   └── test/                               # Test classes
├── pom.xml                                 # Maven dependencies
└── README.md                               # Project documentation
```
## Key Components
### Models
- **User**: Represents a user who can create reminders
- **Reminder**: Contains payment information including amount, description, recipient email, and status

### Services
- **ReminderService**: Manages reminder creation, scheduling, and processing
- **EmailService**: Handles email composition and delivery with HTML formatting

### Features in Detail
#### Reminder Creation
Users can create reminders by specifying:
- Payment amount
- Description
- Recipient email
- Due date (optional)

#### Automated Email System
The system sends professionally formatted HTML emails that include:
- Payment details (amount, description)
- The name and contact information of the user who created the reminder
- Information about the automated nature of the reminder
- A counter showing which reminder number is being sent
- Warning that reminders will increase in frequency if payment is not made

#### Weekly Reminder Processing
The system automatically processes outstanding reminders on a weekly basis, sending notifications for payments that remain unpaid.
## Setup and Installation
### Prerequisites
- Java 17 or higher
- Maven
- SMTP server access for email functionality

### Configuration
1. Clone the repository
2. Configure the file with your database and email settings: `application.properties`
``` properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/payment_reminder_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Email Configuration
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your_email@example.com
spring.mail.password=your_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
### Running the Application
``` bash
mvn spring-boot:run
```
## API Endpoints
### Reminders
- `POST /api/reminders` - Create a new reminder
- `GET /api/reminders` - Get all reminders
- `GET /api/reminders/{id}` - Get a specific reminder
- `PUT /api/reminders/{id}` - Update a reminder
- `DELETE /api/reminders/{id}` - Delete a reminder

### Users
- `POST /api/users` - Create a new user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get a specific user

## Future Enhancements
- Payment gateway integration
- Analytics dashboard for tracking reminder effectiveness
- Customizable reminder templates
- SMS notifications in addition to emails
- Internationalization and multi-language support
