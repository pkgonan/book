package io.book.search.domain;

import org.springframework.data.domain.Pageable;

public interface Search {

    SearchResult search(String query, Pageable pageable);

}