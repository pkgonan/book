package io.book.search.domain;

import org.springframework.data.domain.Page;

public abstract class SearchEvent {

    private final Page<Document> documents;

    SearchEvent(final Page<Document> documents) {
        this.documents = documents;
    }

    public Page<Document> getDocuments() {
        return documents;
    }
}