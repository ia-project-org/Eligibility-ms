package org.eligibilityms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * this entity stores the client eligibility result on different dates
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EligibilityStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eligibilityId;
    private Date lastCheckedDate;
    private String eligibilityResult;
    /**
     *   this result is related to the client with this clientId
     */
    private Long clientId;

    public EligibilityStatus() {

    }
}
