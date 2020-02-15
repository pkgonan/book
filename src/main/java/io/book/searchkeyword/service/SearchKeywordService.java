package io.book.searchkeyword.service;

import io.book.searchkeyword.domain.SearchKeyword;
import io.book.searchkeyword.domain.SearchKeywordRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SearchKeywordService {

    private final SearchKeywordRepository repository;

    SearchKeywordService(final SearchKeywordRepository repository) {
        this.repository = repository;
    }

    public Flux<SearchKeywordDto> getTop(long N) {
        return repository.top(SearchKeyword.SEARCH_KEYWORD_KEY, N)
                .flatMap(searchKeyword -> Mono.justOrEmpty(toDto(searchKeyword)));
    }

    private SearchKeywordDto toDto(final SearchKeyword searchKeyword) {
        SearchKeywordDto dto = new SearchKeywordDto();
        dto.setKeyword(searchKeyword.getKeyword());
        dto.setCount(searchKeyword.getCount());
        return dto;
    }
}