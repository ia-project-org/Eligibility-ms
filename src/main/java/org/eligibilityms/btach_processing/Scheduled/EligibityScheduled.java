package org.eligibilityms.btach_processing.Scheduled;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EligibityScheduled {


    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("EligibilityJob")
    private Job ClientEligibilityJob;

    @Autowired
    @Qualifier("RecommendationJob")
    private Job RecommendationJob;

    public EligibityScheduled(JobLauncher jobLauncher,@Qualifier("EligibilityJob") Job clientEligibilityJob,@Qualifier("RecommendationJob") Job recommendationJob) {
        this.jobLauncher = jobLauncher;
        ClientEligibilityJob = clientEligibilityJob;
        RecommendationJob = recommendationJob;
    }

    @Scheduled(cron = "0 50 13 * * ?")
    public void startjob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()

                .addString("chunkSize", "50")  // Example value

                .addString("timestamp", String.valueOf(System.currentTimeMillis())) // Unique parameter

                .toJobParameters();
        jobLauncher.run(ClientEligibilityJob,jobParameters);
    }

    @Scheduled(cron = "0 55 13 * * ?")
    public void StartRecommendJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()

                .addString("chunkSize", "30")  // Example value

                .addString("timestamp", String.valueOf(System.currentTimeMillis())) // Unique parameter

                .toJobParameters();
        jobLauncher.run(RecommendationJob,jobParameters);
    }
}