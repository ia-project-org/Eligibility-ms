package org.eligibilityms.btach_processing.Processor;

import org.eligibilityms.dto.ClientDetailsDto;
import org.eligibilityms.model.EligibilityStatus;
import org.eligibilityms.proxy.IaModelMsProxy;
import org.springframework.batch.item.ItemProcessor;

import static org.eligibilityms.mapper.ClientLoanMapper.toLoanDto;

public class RecommendationProcessor implements ItemProcessor<ClientDetailsDto, EligibilityStatus> {

    private final IaModelMsProxy iaModelMsProxy;

    public RecommendationProcessor(IaModelMsProxy iaModelMsProxy) {
        this.iaModelMsProxy = iaModelMsProxy;
    }

    @Override
    public EligibilityStatus process(ClientDetailsDto clientDetailsDto) throws Exception {

        iaModelMsProxy.RecommendCreditsToClient(toLoanDto(clientDetailsDto));
        return null;
    }
}
