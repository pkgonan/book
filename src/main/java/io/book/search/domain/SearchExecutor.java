package io.book.search.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
                final SearchResult searchResult = search.search(query, pageable);
                final PageImpl<Document> page = new PageImpl<>(searchResult.getDocuments(), pageable, searchResult.getMetadata().getTotal());
                return SearchExecuted.of(page, memberId, query, pageable);
            } catch (Exception e) {
                log.error("Failure to search by (memberId : {}, query : {}, pageable : {})", memberId, query, pageable);
            }
        }
        return SearchExecuted.of(Page.empty(), memberId, query, pageable);
    }
}