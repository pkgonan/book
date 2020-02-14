package io.book.search.domain;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

class SearchSuccessTest extends SearchTest {

    @Test
    void searchSuccessTest() {
        SearchExecutor executor = new SearchExecutor(generateSearches());
        Page<Document> book = executor.execute("book", Pageable.unpaged());
        int bookSize = book.getSize();

        Assertions.assertTrue(book.hasContent());
        Assertions.assertEquals(2, bookSize);
    }

    private List<Search> generateSearches() {
        final List<Search> searches = Lists.newArrayList();
        searches.add(new SuccessKakaoSearch());
        searches.add(new SuccessNaverSearch());
        return searches;
    }

    private static class SuccessKakaoSearch implements Search {
        @Override
        public Page<Document> search(String query, Pageable pageable) {
            final List<Document> documents = Lists.newArrayList();
            documents.add(generateDocument());
            documents.add(generateDocument());

            return new PageImpl<>(documents);
        }
    }

    private static class SuccessNaverSearch extends SuccessKakaoSearch {
    }
}