package org.eligibilityms.proxy;

import org.eligibilityms.dto.ClientDetailsDto;
import org.eligibilityms.dto.LoanDto;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *  Feign for communication with IaModel-ms service
 *  Ribbon for loadBalancing
 */
@FeignClient(name = "IAMODEL-MS")
@RibbonClient(name = "IAMODEL-MS")
public interface IaModelMsProxy {

    /***
    /* method to evaluate client eligibility from the ia model service
    ***/
    @PostMapping("/app/predict/")
    ResponseEntity<?> evaluateClientEligibility(
            @RequestBody ClientDetailsDto clientDetailsDto);

    /***
     /* method to recommend credits to eligible client
     ***/
    @PostMapping("/app/recommend/")
    ResponseEntity<?> RecommendCreditsToClient(
            @RequestBody LoanDto loanDto);
}
