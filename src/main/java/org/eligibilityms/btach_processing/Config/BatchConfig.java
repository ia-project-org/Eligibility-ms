package org.eligibilityms.btach_processing.Config;

import lombok.RequiredArgsConstructor;
import org.eligibilityms.btach_processing.Listener.EligibilityJobListener;
import org.eligibilityms.btach_processing.Listener.RecommendationListener;
import org.eligibilityms.btach_processing.Processor.EligibilityProcessor;
import org.eligibilityms.btach_processing.Processor.RecommendationProcessor;
import org.eligibilityms.btach_processing.Reader.FeignPagingItemReader;
import org.eligibilityms.dto.ClientDetailsDto;
import org.eligibilityms.model.EligibilityStatus;
import org.eligibilityms.proxy.BankMsProxy;
import org.eligibilityms.proxy.IaModelMsProxy;
import org.eligibilityms.repository.EligibilityStatusRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {

    private final JobRepository jobRepository;

    private final EligibilityStatusRepository eligibilityStatusRepository;

    private final IaModelMsProxy iaModelMsProxy;

    private final BankMsProxy bankMsProxy;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public FeignPagingItemReader clientDetailsReader(BankMsProxy clientFeignClient) {
        return new FeignPagingItemReader(clientFeignClient, getChunkSize()); // Taille du chunk
    }

    @Bean
    public EligibilityProcessor clientEligibilityProcessor(){
        return new EligibilityProcessor(iaModelMsProxy);
    }

    @Bean
    public RecommendationProcessor creditsRecommendationProcessor(){
        return new RecommendationProcessor(iaModelMsProxy);
    }

    @Bean
    public RepositoryItemWriter<EligibilityStatus> clientEligibilityWriter(){
       RepositoryItemWriter<EligibilityStatus> writer = new RepositoryItemWriter<EligibilityStatus>();
       writer.setRepository(eligibilityStatusRepository);
       writer.setMethodName("save");
       return writer;
   }


    @Bean(name = "EligibilityStep")
    public Step EligibilityStep(BankMsProxy bankMsProxy){
       return new StepBuilder("EligibilityStep",jobRepository)
                .<ClientDetailsDto, EligibilityStatus>chunk( getChunkSize(), platformTransactionManager)
                .reader(clientDetailsReader(bankMsProxy))
                .processor(clientEligibilityProcessor())
                .writer(clientEligibilityWriter())
                .allowStartIfComplete(true)
                .build();
   }


    @Bean(name = "RecommendationStep")
    public Step RecommendationStep(BankMsProxy bankMsProxy){
        return new StepBuilder("RecommendationStep",jobRepository)
                .<ClientDetailsDto, EligibilityStatus>chunk( getChunkSize(), platformTransactionManager)
                .reader(clientDetailsReader(bankMsProxy))
                .processor(creditsRecommendationProcessor())
                .writer(clientEligibilityWriter())
                .allowStartIfComplete(true)
                .build();
    }


    @Bean(name = "EligibilityJob")
    public Job EligibilityJob(EligibilityJobListener eligibilityJobListener){
        return new JobBuilder("ClientEligibilityJob",jobRepository)
                .listener(eligibilityJobListener)
                .start(EligibilityStep(bankMsProxy))
                .build();
    }

    @Bean(name = "RecommendationJob")
    public Job RecommendationJob(RecommendationListener recommendationListener){
        return new JobBuilder("RecommendationJob",jobRepository)
                .listener(recommendationListener)
                .start(RecommendationStep(bankMsProxy))
                .build();
    }

    public int getChunkSize(){
       String value = System.getProperty("chunkSize");
       return value != null ? Integer.parseInt(value) : 20;
   }

}
