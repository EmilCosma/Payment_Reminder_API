package com.Payment_Reminder_API.repositories;

import com.Payment_Reminder_API.models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByIsPaidFalse();

}
