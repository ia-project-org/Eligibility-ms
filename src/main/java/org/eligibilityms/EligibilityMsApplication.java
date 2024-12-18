package org.eligibilityms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("org.eligibilityms")
@EnableScheduling
public class EligibilityMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EligibilityMsApplication.class, args);
    }
}
