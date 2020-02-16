package io.book.search.domain;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

class SearchFailoverTest extends SearchTest {

    @Test
    void searchFailoverTest() {
        SearchExecutor executor = new SearchExecutor(generateSearches());
        SearchExecuted event = executor.execute(1L, "book", Pageable.unpaged());
        Page<Document> book = event.getDocuments();
        int bookSize = book.getSize();

        Assertions.assertTrue(book.hasContent());
        Assertions.assertEquals(2, bookSize);
    }

    private List<Search> generateSearches() {
        final List<Search> searches = Lists.newArrayList();
        searches.add(new FailureKakaoSearch());
        searches.add(new SuccessNaverSearch());
        return searches;
    }

    private static class FailureKakaoSearch implements Search {
        @Override
        public SearchResult search(String query, Pageable pageable) {
            throw new IllegalArgumentException();
        }
    }

    private static class SuccessNaverSearch implements Search {
        @Override
        public SearchResult search(String query, Pageable pageable) {
            return generateSearchResult();
        }
    }
}