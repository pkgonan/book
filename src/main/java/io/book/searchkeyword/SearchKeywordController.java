package io.book.searchkeyword;

import io.book.searchkeyword.exception.InvalidParameterRangeException;
import io.book.searchkeyword.service.SearchKeywordDto;
import io.book.searchkeyword.service.SearchKeywordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Validated
@RestController
public class SearchKeywordController {

    private final SearchKeywordService searchKeywordService;

    SearchKeywordController(final SearchKeywordService searchKeywordService) {
        this.searchKeywordService = searchKeywordService;
    }

    @GetMapping("/search-keywords/top/{N}")
    public Flux<SearchKeywordDto> top(@PathVariable final Long N) {
        if (N < 1L || N > 10L) {
            throw new InvalidParameterRangeException();
        }
        return searchKeywordService.getTop(N);
    }
}