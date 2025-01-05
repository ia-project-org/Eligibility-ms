package org.eligibilityms.controller;

import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eligibilityms.dto.ClientDetailsDto;
import org.eligibilityms.model.Notification;
import org.eligibilityms.proxy.BankMsProxy;
import org.eligibilityms.proxy.IaModelMsProxy;
import org.eligibilityms.service.EligibilityStatusService;
import org.eligibilityms.service.NotificationService;
import org.eligibilityms.util.DateUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.eligibilityms.mapper.ClientLoanMapper.toLoanDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eligibility")
public class EligibilityController {

    private final EligibilityStatusService eligibilityStatusService;

    private final NotificationService notificationService;

    private final JobLauncher jobLauncher;

    private final IaModelMsProxy iaModelMsProxy;

    private final Job EligibilityJob;

    /**
     * get the eligibility status for a single client
     *
     * @param clientId
     * @return
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientEligibilityStatus(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok().body(eligibilityStatusService.getClientEligibilityStatus(clientId));
    }

    /**
     * evaluate client eligibility and save the result in the database
     *
     * @param clientId
     * @return
     */
    @PostMapping("/{clientId}")
    public ResponseEntity<?> evaluateClientEligibility(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok().body(eligibilityStatusService.evaluateClientEligibility(clientId));
    }


    @PostMapping("/recomand")
    public ResponseEntity<?> recomand(@RequestBody ClientDetailsDto clientDetailsDto) {
        return ResponseEntity.ok().body(iaModelMsProxy.RecommendCreditsToClient(toLoanDto(clientDetailsDto)));
    }

    @GetMapping("/notifications")
    public ResponseEntity<Page<Notification>> getEligibilityStatusNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Page<Notification> notificationPage =  notificationService.getAllNotifications(page,size);
        return ResponseEntity.ok().body(notificationPage);
    }

    @GetMapping("/batch")
    public ResponseEntity<?> getClientEligibilityStus() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("chunkSize", "20")  // Example value
                .addString("timestamp", String.valueOf(System.currentTimeMillis())) // Unique parameter
                .toJobParameters();
        jobLauncher.run(EligibilityJob,jobParameters);
        return ResponseEntity.ok().body("ok");
    }
    /**
     * get the number of evaluated clients which have an eligibility status = "Good"
     *
     * @return
     */
    @GetMapping("/good-number")
    public ResponseEntity<Integer> countGoodEligibility() {
        return ResponseEntity.ok().body(eligibilityStatusService.countByEligibilityResult("Good"));
    }

    /**
     * get the number of previous month evaluated clients which have an eligibility status = "Good"
     *
     * @return
     */
    @GetMapping("/good-number-previous-month")
    public ResponseEntity<Integer> countGoodEligibilityPreviousMonth() {
        Date previousMonthDate = DateUtils.getPreviousMonthDate();
        return ResponseEntity.ok().body(eligibilityStatusService.countByEligibilityResult("Good", previousMonthDate));
    }

    /**
     * get the number of evaluated clients which have an eligibility status = "Standard"
     *
     * @return
     */
    @GetMapping("/standard-number")
    public ResponseEntity<Integer> countStandardEligibility() {
        return ResponseEntity.ok().body(eligibilityStatusService.countByEligibilityResult("Standard"));
    }

    /**
     * get the number of previous month evaluated clients which have an eligibility status = "Standard"
     *
     * @return
     */
    @GetMapping("/standard-number-previous-month")
    public ResponseEntity<Integer> countStandardEligibilityPreviousMonth() {
        Date previousMonthDate = DateUtils.getPreviousMonthDate();
        return ResponseEntity.ok().body(eligibilityStatusService.countByEligibilityResult("Standard", previousMonthDate));
    }

    /**
     * get the number of evaluated clients which have an eligibility status = "Poor"
     *
     * @return
     */
    @GetMapping("/poor-number")
    public ResponseEntity<Integer> countPoorEligibility() {
        return ResponseEntity.ok().body(eligibilityStatusService.countByEligibilityResult("Poor"));
    }

    /**
     * get the number of previous month evaluated clients which have an eligibility status = "Poor"
     *
     * @return
     */
    @GetMapping("/poor-number-previous-month")
    public ResponseEntity<Integer> countPoorEligibilityPreviousMonth() {
        Date previousMonthDate = DateUtils.getPreviousMonthDate();
        return ResponseEntity.ok().body(eligibilityStatusService.countByEligibilityResult("Poor", previousMonthDate));
    }
}
