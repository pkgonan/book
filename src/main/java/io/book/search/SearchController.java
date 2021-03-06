package io.book.search;

import io.book.search.exception.InvalidPageRangeException;
import io.book.search.exception.InvalidPageSizeException;
import io.book.search.service.DocumentDto;
import io.book.search.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class SearchController {

    private final SearchService searchService;

    SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Page<DocumentDto> search(@RequestParam final Long memberId,
                                    @RequestParam final String query,
                                    @PageableDefault(page = 1, size = 10) final Pageable pageable) {

        if (pageable.getPageNumber() < 1) {
            throw new InvalidPageRangeException();
        }
        if (pageable.getPageSize() > 50) {
            throw new InvalidPageSizeException();
        }

        return searchService.search(memberId, query, pageable);
    }
}