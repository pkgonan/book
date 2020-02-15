package io.book.searchkeyword.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@EqualsAndHashCode(of = "keyword", callSuper = false)
public class SearchKeyword {

    public static final String SEARCH_KEYWORD_KEY = "search:keyword";

    @NotBlank
    private String keyword;

    private Long count;

    public static SearchKeyword of(final String keyword, final Long count) {
        SearchKeyword searchKeyword = new SearchKeyword();
        searchKeyword.keyword = keyword;
        searchKeyword.count = count;

        return searchKeyword;
    }
}
