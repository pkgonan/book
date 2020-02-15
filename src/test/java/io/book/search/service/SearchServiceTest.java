package io.book.search.service;

import io.book.search.domain.SearchExecuted;
import io.book.search.domain.SearchExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Test
    void searchTest() {
        long memberId = 1L;
        String query = "hello";
        Pageable pageable = Pageable.unpaged();

        ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
        SearchExecuted event = mock(SearchExecuted.class);
        doReturn(Page.empty()).when(event).getDocuments();
        SearchExecutor executor = mock(SearchExecutor.class);
        doReturn(event).when(executor).execute(memberId, query, pageable);

        SearchService searchService = new SearchService(executor);
        searchService.setApplicationEventPublisher(eventPublisher);
        searchService.search(memberId, query, pageable);

        verify(executor, times(1)).execute(memberId, query, pageable);
        verify(eventPublisher, times(1)).publishEvent(event);
    }
}