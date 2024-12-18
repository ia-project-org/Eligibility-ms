package org.eligibilityms.btach_processing.Listener;

import lombok.RequiredArgsConstructor;
import org.eligibilityms.model.Notification;
import org.eligibilityms.service.NotificationService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RecommendationListener implements JobExecutionListener {
    private final NotificationService notificationService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Le job commence...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Initialiser une notification
        Notification notification = new Notification();
        notification.setJobName(jobExecution.getJobInstance().getJobName());
        notification.setNotificationDate(LocalDateTime.now());
        // Définir le titre et le message en fonction de l'état du job
        if (jobExecution.getStatus().isUnsuccessful()) {
            System.out.println("failed");
            notification.setTitle("Credit Recommendation Process Unsuccessful");
            notification.setMessage("We were unable to generate credit recommendations for the requested clients.");
            notification.setJobState("UNSUCCESSFUL");
            notificationService.sendNotification(notification);
        } else {
            System.out.println("success");
            notification.setTitle("Credit Recommendation Process Successful");
            notification.setMessage("Credit recommendations for the requested clients have been successfully generated.");
            notification.setJobState("SUCCESS");
            notificationService.sendNotification(notification);
        }
    }
}
