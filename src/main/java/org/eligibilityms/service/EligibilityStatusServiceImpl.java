package org.eligibilityms.service;

import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.eligibilityms.model.EligibilityStatus;
import org.eligibilityms.proxy.BankMsProxy;
import org.eligibilityms.proxy.IaModelMsProxy;
import org.eligibilityms.repository.EligibilityStatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class EligibilityStatusServiceImpl implements EligibilityStatusService{

    private final BankMsProxy clientsMsProxy;

    private final IaModelMsProxy iaModelMsProxy;

    private final EligibilityStatusRepository eligibilityStatusRepository;

    /**
     * save client eligibility result in the database
     * @param result
     * @param clientId
     * @return
     */
    @Override
    public EligibilityStatus saveClientEligibilityStatus(String result, Long clientId) {
        return eligibilityStatusRepository.save(
                EligibilityStatus.builder()
                        .eligibilityResult(result)
                        .lastCheckedDate(new Date())
                        .clientId(clientId)
                        .build()
        );
    }

    /**
     * get the eligibility status for a single client
     * @param clientId
     * @return
     */
    @Override
    public EligibilityStatus getClientEligibilityStatus(Long clientId) {
        EligibilityStatus eligibilityStatus = eligibilityStatusRepository.findLatestEligibilityStatusByClientId(clientId);
        if(eligibilityStatus==null){
            evaluateClientEligibility(clientId);
            return eligibilityStatusRepository.findLatestEligibilityStatusByClientId(clientId);
        }else
            return eligibilityStatus;
    }

    @Override
    public ResponseEntity<EligibilityStatus> evaluateClientEligibility(Long clientId) {
        String creditScore = JsonPath.parse(
                        iaModelMsProxy.evaluateClientEligibility(
                                clientsMsProxy.getClientDetails(clientId)
                        ).getBody())
                .read("$.credit_score");
        return ResponseEntity.ok().body(saveClientEligibilityStatus(creditScore, clientId));
    }

    @Override
    public Integer countByEligibilityResult(String eligibilityResult) {
        return eligibilityStatusRepository.countUniqueClientsByEligibilityResult(eligibilityResult);
    }

    @Override
    public Integer countByEligibilityResult(String eligibilityResult, Date lastMonthDate) {
        return eligibilityStatusRepository.countUniqueEligibilityBeforeLastMonth(eligibilityResult, lastMonthDate);
    }
}
