package com.donation.repository;

import com.donation.models.data.Notification;
import com.donation.models.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
<<<<<<< HEAD
    List<Notification> findByUserOrderByTimestampDesc(User user);

=======
    List<Notification> findByUser(User user);

    List<Notification> findByUserAndIsReadFalse(User user);
>>>>>>> c20a9643e4d9e22674313fe21adedf9df48d2ec9
}
