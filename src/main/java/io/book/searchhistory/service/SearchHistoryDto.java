package io.book.searchhistory.service;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class SearchHistoryDto {
    private final Long id;
    private final Long memberId;
    private final String keyword;
    private final OffsetDateTime createdAt;
}