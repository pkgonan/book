package io.book.search.domain;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

class SearchFailureTest extends SearchTest {

    @Test
    void searchFailureTest() {
        SearchExecutor executor = new SearchExecutor(generateSearches());
        SearchExecuted event = executor.execute(1L, "book", Pageable.unpaged());
        Page<Document> book = event.getDocuments();
        int bookSize = book.getSize();

        Assertions.assertFalse(book.hasContent());
        Assertions.assertEquals(0, bookSize);
    }

    private List<Search> generateSearches() {
        final List<Search> searches = Lists.newArrayList();
        searches.add(new FailureKakaoSearch());
        searches.add(new FailureNaverSearch());
        return searches;
    }

    private static class FailureKakaoSearch implements Search {
        @Override
        public Page<Document> search(String query, Pageable pageable) {
            throw new IllegalArgumentException();
        }
    }

    private static class FailureNaverSearch extends FailureKakaoSearch {
    }
}