package io.book.search.domain;

import java.util.Collections;
import java.util.List;

public class SearchResult {

    private final Metadata metadata;
    private final List<Document> documents;

    SearchResult(final Metadata metadata, final List<Document> documents) {
        this.metadata = metadata;
        this.documents = documents;
    }

    public long getTotal() {
        return metadata.getTotal();
    }

    public List<Document> getDocuments() {
        return Collections.unmodifiableList(documents);
    }
}