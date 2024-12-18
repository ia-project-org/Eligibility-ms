package org.eligibilityms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;         // Title of the notification
    private String message;       // Detailed message about the job state

    private String jobName;       // Name of the Spring Batch job
    private String jobState;      // State of the job (e.g., STARTED, COMPLETED, FAILED)
    private LocalDateTime notificationDate; // Date and time of the notification

}
