package org.eligibilityms.btach_processing.Reader;

import org.eligibilityms.dto.ClientDetailsDto;
import org.eligibilityms.proxy.BankMsProxy;
import org.springframework.batch.item.ItemReader;

import java.util.Iterator;
import java.util.List;


public class FeignPagingItemReader implements ItemReader<ClientDetailsDto> {

    private final BankMsProxy bankMsProxy;

    private final int pageSize;
    private int currentPage = 0;
    private Iterator<ClientDetailsDto> currentDataIterator;
    private boolean isLastPage = false;

    public FeignPagingItemReader(BankMsProxy bankMsProxy, int pageSize) {
        this.bankMsProxy = bankMsProxy;
        this.pageSize = pageSize;
    }


    @Override
    public ClientDetailsDto read() {
        if ((currentDataIterator == null || !currentDataIterator.hasNext()) && !isLastPage) {
            fetchNextPage();
        }
        return currentDataIterator != null && currentDataIterator.hasNext() ? currentDataIterator.next() : null;
    }

    private void fetchNextPage() {
        BankMsProxy.PaginatedResponse<ClientDetailsDto> response = bankMsProxy.getClientsDetailsList(currentPage, pageSize);
        List<ClientDetailsDto> data = response.getContent();
        isLastPage = response.isLast();
        currentDataIterator = data.iterator();
        currentPage++;
    }
}

