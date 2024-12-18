package org.eligibilityms.repository;

import org.eligibilityms.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n ORDER BY n.notificationDate DESC")
    Page<Notification> findLatestPublicNotifications(Pageable pageable);

}
