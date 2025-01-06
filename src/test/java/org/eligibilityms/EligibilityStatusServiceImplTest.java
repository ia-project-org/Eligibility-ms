package org.eligibilityms;

import com.jayway.jsonpath.JsonPath;
import org.eligibilityms.model.EligibilityStatus;
import org.eligibilityms.proxy.BankMsProxy;
import org.eligibilityms.proxy.IaModelMsProxy;
import org.eligibilityms.repository.EligibilityStatusRepository;
import org.eligibilityms.service.EligibilityStatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EligibilityStatusServiceImplTest {

    @Mock
    private BankMsProxy bankMsProxy;

    @Mock
    private IaModelMsProxy iaModelMsProxy;

    @Mock
    private EligibilityStatusRepository eligibilityStatusRepository;

    @InjectMocks
    private EligibilityStatusServiceImpl eligibilityStatusService;

    private EligibilityStatus eligibilityStatus;
    private Long clientId;
    private String eligibilityResult;

    @BeforeEach
    void setUp() {
        clientId = 1L;
        eligibilityResult = "ELIGIBLE";

        eligibilityStatus = EligibilityStatus.builder()
                .eligibilityResult(eligibilityResult)
                .lastCheckedDate(new Date())
                .clientId(clientId)
                .build();
    }

    @Test
    void testSaveClientEligibilityStatus() {
        when(eligibilityStatusRepository.save(any(EligibilityStatus.class))).thenReturn(eligibilityStatus);

        EligibilityStatus savedStatus = eligibilityStatusService.saveClientEligibilityStatus(eligibilityResult, clientId);

        assertNotNull(savedStatus);
        assertEquals(clientId, savedStatus.getClientId());
        assertEquals(eligibilityResult, savedStatus.getEligibilityResult());
        verify(eligibilityStatusRepository, times(1)).save(any(EligibilityStatus.class));
    }

    @Test
    void testGetClientEligibilityStatus_WhenStatusExists() {
        when(eligibilityStatusRepository.findLatestEligibilityStatusByClientId(clientId)).thenReturn(eligibilityStatus);

        EligibilityStatus result = eligibilityStatusService.getClientEligibilityStatus(clientId);

        assertNotNull(result);
        assertEquals(clientId, result.getClientId());
        assertEquals(eligibilityResult, result.getEligibilityResult());
        verify(eligibilityStatusRepository, times(1)).findLatestEligibilityStatusByClientId(clientId);
        verify(eligibilityStatusRepository, never()).save(any(EligibilityStatus.class));
    }





    @Test
    void testCountByEligibilityResult() {
        when(eligibilityStatusRepository.countUniqueClientsByEligibilityResult(eligibilityResult)).thenReturn(10);

        Integer count = eligibilityStatusService.countByEligibilityResult(eligibilityResult);

        assertNotNull(count);
        assertEquals(10, count);
        verify(eligibilityStatusRepository, times(1)).countUniqueClientsByEligibilityResult(eligibilityResult);
    }

    @Test
    void testCountByEligibilityResult_WithDate() {
        Date lastMonthDate = new Date();
        when(eligibilityStatusRepository.countUniqueEligibilityBeforeLastMonth(eligibilityResult, lastMonthDate)).thenReturn(5);

        Integer count = eligibilityStatusService.countByEligibilityResult(eligibilityResult, lastMonthDate);

        assertNotNull(count);
        assertEquals(5, count);
        verify(eligibilityStatusRepository, times(1)).countUniqueEligibilityBeforeLastMonth(eligibilityResult, lastMonthDate);
    }
}