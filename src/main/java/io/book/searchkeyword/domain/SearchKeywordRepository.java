package io.book.searchkeyword.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SearchKeywordRepository {

    Flux<SearchKeyword> top(String key, long N);

    Mono<Long> incrementAndGet(String key, String keyword);
}