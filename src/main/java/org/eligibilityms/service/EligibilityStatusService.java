package org.eligibilityms.service;

import org.eligibilityms.model.EligibilityStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface EligibilityStatusService {

    /**
     * save client eligibility result in the database
     * @param clientId
     * @return
     */
    EligibilityStatus saveClientEligibilityStatus(String result, Long clientId);

    /**
     * get the eligibility status for a single client
     * @param clientId
     * @return
     */
    EligibilityStatus getClientEligibilityStatus(Long clientId);

    ResponseEntity<EligibilityStatus> evaluateClientEligibility(Long clientId);

    /**
     * get the number of clients with an eligibility status equal to Good
     * @param eligibilityResult
     * @return
     */
    Integer countByEligibilityResult(String eligibilityResult);

    /**
     * get the number of clients with an eligibility status equal to Good who where last checked last month
     * @param eligibilityResult
     * @param lastMonthDate
     * @return
     */
    Integer countByEligibilityResult(String eligibilityResult, Date lastMonthDate);
}
