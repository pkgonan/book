package io.book.searchhistory.service;

import com.querydsl.core.types.Predicate;
import io.book.searchhistory.domain.SearchHistory;
import io.book.searchhistory.domain.SearchHistoryRepository;
import io.book.searchhistory.domain.SearchHistorySpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchHistoryService {

    private final SearchHistoryRepository repository;

    SearchHistoryService(final SearchHistoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<SearchHistoryDto> page(final long memberId, final Pageable pageable) {
        final Predicate predicate = SearchHistorySpecs.memberId(memberId);
        return toPageDto(repository.findAll(predicate, pageable));
    }

    private Page<SearchHistoryDto> toPageDto(final Page<SearchHistory> entityPage) {
        return entityPage.map(this::toDto);
    }

    private SearchHistoryDto toDto(final SearchHistory entity) {
        return new SearchHistoryDto(entity.getId(), entity.getMemberId(), entity.getKeyword(), entity.getCreatedAt());
    }
}