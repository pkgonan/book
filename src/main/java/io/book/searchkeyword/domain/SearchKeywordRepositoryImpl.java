package io.book.searchkeyword.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
class SearchKeywordRepositoryImpl implements SearchKeywordRepository {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    SearchKeywordRepositoryImpl(final ReactiveStringRedisTemplate reactiveStringRedisTemplate) {
        this.reactiveStringRedisTemplate = reactiveStringRedisTemplate;
    }

    @Override
    public Flux<SearchKeyword> top(final String key, final long N) {
        return reactiveStringRedisTemplate.opsForZSet()
                .reverseRangeWithScores(key, Range.from(Range.Bound.inclusive(0L)).to(Range.Bound.exclusive(N)))
                .doOnError(e -> log.error("Search Keyword Repository Exception : (key : {}, N : {})", key, N))
                .flatMap(tuple -> Mono.justOrEmpty(SearchKeyword.of(tuple.getValue(), Objects.requireNonNull(tuple.getScore()).longValue())));
    }

    @Override
    public Mono<Long> incrementAndGet(final String key, final String keyword) {
        return reactiveStringRedisTemplate.opsForZSet()
                .incrementScore(key, keyword, 1D)
                .flatMap(val -> Mono.justOrEmpty(val.longValue()));
    }
}