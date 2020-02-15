package io.book.searchhistory.domain;

import com.querydsl.core.types.dsl.BooleanExpression;

public class SearchHistorySpecs {

    private static final QSearchHistory SEARCH_HISTORY = QSearchHistory.searchHistory;

    private SearchHistorySpecs() {}

    public static BooleanExpression memberId(final long memberId) {
        return SEARCH_HISTORY.memberId.eq(memberId);
    }
}