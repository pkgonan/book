package io.book.searchhistory;

import io.book.searchhistory.service.SearchHistoryDto;
import io.book.searchhistory.service.SearchHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    SearchHistoryController(final SearchHistoryService searchHistoryService) {
        this.searchHistoryService = searchHistoryService;
    }

    @GetMapping("/search-histories")
    public Page<SearchHistoryDto> page(
            @RequestParam final Long memberId,
            @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return searchHistoryService.page(memberId, pageable);
    }
}