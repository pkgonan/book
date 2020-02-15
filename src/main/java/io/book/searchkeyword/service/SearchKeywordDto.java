package io.book.searchkeyword.service;

import lombok.Data;

@Data
public class SearchKeywordDto {
    private String keyword;
    private Long count;
}