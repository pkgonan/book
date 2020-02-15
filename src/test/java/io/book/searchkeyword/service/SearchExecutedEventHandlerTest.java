package io.book.searchkeyword.service;

import io.book.search.domain.SearchExecuted;
import io.book.searchkeyword.domain.SearchKeyword;
import io.book.searchkeyword.domain.SearchKeywordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

class SearchExecutedEventHandlerTest {

    @Test
    void onSearchEventExecuted() {
        SearchKeywordRepository repository = spy(new TestRepository());
        String keyword = "테스트북";
        long memberId = 1000L;

        SearchExecuted event = SearchExecuted.of(Page.empty(), memberId, keyword, Pageable.unpaged());
        SearchExecutedEventHandler searchExecutedEventHandler = new SearchExecutedEventHandler(repository);
        searchExecutedEventHandler.onSearchExecuted(event);

        verify(repository, times(1)).incrementAndGet(SearchKeyword.SEARCH_KEYWORD_KEY, keyword);
    }

    private static class TestRepository implements SearchKeywordRepository {

        @Override
        public Flux<SearchKeyword> top(String key, long N) {
            return Flux.empty();
        }

        @Override
        public Mono<Long> incrementAndGet(String key, String keyword) {
            return Mono.justOrEmpty(1L);
        }
    }
}