package io.book.searchhistory.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>, QuerydslPredicateExecutor<SearchHistory> {
}