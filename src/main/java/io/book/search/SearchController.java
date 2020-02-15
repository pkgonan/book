package io.book.search;

import io.book.member.infra.MemberIdSessionExtractor;
import io.book.search.service.DocumentDto;
import io.book.search.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Validated
@RestController
public class SearchController {

    private final SearchService searchService;

    SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Page<DocumentDto> search(@RequestParam final String query,
                                     @PageableDefault(size = 100) final Pageable pageable,
                                     final HttpSession session) {
        final long memberId = MemberIdSessionExtractor.extract(session);
        return searchService.search(memberId, query, pageable);
    }
}