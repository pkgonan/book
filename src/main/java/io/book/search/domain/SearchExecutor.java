package io.book.search.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SearchExecutor {

    private final List<Search> searchs;

    public SearchExecutor(final List<Search> searches) {
        this.searchs = searches;
    }

    public SearchExecuted execute(final long memberId, final String query, final Pageable pageable) {
        for (Search search : searchs) {
            try {
                final Page<Document> searchResult = search.search(query, pageable);
                return SearchExecuted.of(searchResult, memberId, query, pageable);
            } catch (Exception e) {
                log.error("Failure to search by (query : {}, pageable : {})", query, pageable);
            }
        }
        return SearchExecuted.of(Page.empty(), memberId, query, pageable);
    }
}