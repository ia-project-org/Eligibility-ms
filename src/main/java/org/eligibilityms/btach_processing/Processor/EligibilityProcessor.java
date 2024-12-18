package org.eligibilityms.btach_processing.Processor;

import com.jayway.jsonpath.JsonPath;
import org.eligibilityms.dto.ClientDetailsDto;
import org.eligibilityms.model.EligibilityStatus;
import org.eligibilityms.proxy.IaModelMsProxy;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;
public class EligibilityProcessor implements ItemProcessor<ClientDetailsDto,EligibilityStatus> {

    private final IaModelMsProxy iaModelMsProxy;

    public EligibilityProcessor(IaModelMsProxy iaModelMsProxy) {
        this.iaModelMsProxy = iaModelMsProxy;
    }

    @Override
    public EligibilityStatus process(ClientDetailsDto clientDetailsDto) throws Exception {
        String creditScore = JsonPath.parse(iaModelMsProxy.evaluateClientEligibility(clientDetailsDto).getBody())
                .read("$.credit_score");
        return EligibilityStatus.builder()
                .clientId(clientDetailsDto.getClientId())
                .eligibilityResult(creditScore)
                .lastCheckedDate(new Date())
                .build();
    }
}
