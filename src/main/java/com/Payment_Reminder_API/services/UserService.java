package com.Payment_Reminder_API.services;

import com.Payment_Reminder_API.models.Reminder;
import com.Payment_Reminder_API.models.User;
import com.Payment_Reminder_API.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String getUserDetailsByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            User foundUser = user.get();
            StringBuilder response = new StringBuilder();
            response.append("username: ").append(foundUser.getUsername())
                    .append("\nreminders: ");

            List<Reminder> reminders = foundUser.getReminders();
            for (Reminder reminder : reminders) {
                response.append("\n- ").append(reminder.getDescription())
                        .append(" (created at: ").append(reminder.getCreatedAt()).append(")");
            }

            return response.toString();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
    public String getAllUsersInfo()
    {
        List<User> users = userRepository.findAll();
        StringBuilder response = new StringBuilder();
        for (User user : users) {
            response.append("email: ").append(user.getEmail())
                    .append("\nid: ").append(user.getId())
                    .append("\nusername: ").append(user.getUsername())
                    .append("\nreminders: ");

            List<Reminder> reminders = user.getReminders();
            for (Reminder reminder : reminders) {
                response.append("\n- ").append(reminder.getDescription())
                        .append(" (created at: ").append(reminder.getCreatedAt()).append(")");
            }
        }
        return response.toString();
    }
    //add user
    public String addUser(User user) {
        userRepository.save(user);
        return "User added successfully";
    }

    public UserDetails loadUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s not found", userEmail)
                ));
    }
}
