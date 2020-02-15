package io.book.searchkeyword.service;

import io.book.search.domain.SearchExecuted;
import io.book.searchkeyword.domain.SearchKeyword;
import io.book.searchkeyword.domain.SearchKeywordRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("SearchExecutedEventHandlerInKeyword")
class SearchExecutedEventHandler {

    private final SearchKeywordRepository repository;

    SearchExecutedEventHandler(final SearchKeywordRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void onSearchExecuted(final SearchExecuted event) {
        repository.incrementAndGet(SearchKeyword.SEARCH_KEYWORD_KEY, event.getQuery())
                .subscribe();
    }
}