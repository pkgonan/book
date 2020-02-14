package io.book.search;

import io.book.search.service.DocumentDto;
import io.book.search.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class SearchController {

    private final SearchService searchService;

    SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Page<DocumentDto> isLogin(@PathVariable final String query,
                                     @PageableDefault(size = 100) final Pageable pageable) {
        return searchService.search(query, pageable);
    }
}