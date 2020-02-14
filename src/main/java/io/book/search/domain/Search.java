package io.book.search.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Search {

    Page<Document> search(String query, Pageable pageable);

}
