package io.book.searchhistory.service;

import io.book.search.domain.SearchExecuted;
import io.book.searchhistory.domain.SearchHistory;
import io.book.searchhistory.domain.SearchHistoryRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("SearchExecutedEventHandlerInHistory")
class SearchExecutedEventHandler {

    private final SearchHistoryRepository repository;

    SearchExecutedEventHandler(final SearchHistoryRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void onSearchExecuted(final SearchExecuted event) {
        repository.save(SearchHistory.of(event.getMemberId(), event.getQuery()));
    }
}