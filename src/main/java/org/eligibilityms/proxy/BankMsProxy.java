package org.eligibilityms.proxy;

import lombok.Getter;
import lombok.Setter;
import org.eligibilityms.dto.ClientDetailsDto;
import org.eligibilityms.dto.ClientDto;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *  Feign for communication with Bank-ms service
 *  Ribbon for loadBalancing
 */
@FeignClient(name = "Bank-ms")
@RibbonClient(name = "Bank-ms")
public interface BankMsProxy {

    /**
     * get client details from the bank service
     */
    @GetMapping(value = "/clients/details/{clientId}")
    ClientDetailsDto getClientDetails(@PathVariable Long clientId);

    /**
     * get clients list from the bank service
     * */
    @GetMapping(value = "/clients/details")
    PaginatedResponse<ClientDetailsDto> getClientsDetailsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @Getter
    @Setter
    class PaginatedResponse<T> {
        private List<T> content;
        private boolean last;
    }
}
