package org.eligibilityms.service;


import lombok.AllArgsConstructor;
import org.eligibilityms.model.Notification;
import org.eligibilityms.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Repository
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Notification notification) {
        try {
            Notification savedNotification = notificationRepository.save(notification);
            messagingTemplate.convertAndSend("/topic/public-notifications", savedNotification);
            ResponseEntity.ok("Notification publique envoyée avec succès.");
        } catch (Exception e) {
             ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi de la notification : " + e.getMessage());
        }
    }


    public Page<Notification> getAllNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findLatestPublicNotifications(pageable);
    }
}
