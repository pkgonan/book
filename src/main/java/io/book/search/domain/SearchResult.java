package io.book.search.domain;

import java.util.List;

public interface SearchResult {

    Metadata getMetadata();

    List<Document> getDocuments();

}