package io.book.search.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class SearchExecuted extends SearchEvent {

    private final long memberId;
    private final String query;
    private final Pageable pageable;

    public static SearchExecuted of(final Page<Document> documents, final long memberId, final String query, final Pageable pageable) {
        return new SearchExecuted(documents, memberId, query, pageable);
    }

    private SearchExecuted(final Page<Document> documents, long memberId, final String query, final Pageable pageable) {
        super(documents);
        this.memberId = memberId;
        this.query = query;
        this.pageable = pageable;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getQuery() {
        return query;
    }

    public Pageable getPageable() {
        return pageable;
    }
}