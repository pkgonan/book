package io.book.searchkeyword;

import io.book.searchkeyword.exception.InvalidParameterRangeException;
import io.book.searchkeyword.service.SearchKeywordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class SearchKeywordControllerTest {

    @Test
    void minimumParameterValidationTest() {
        SearchKeywordService searchKeywordService = mock(SearchKeywordService.class);
        SearchKeywordController controller = new SearchKeywordController(searchKeywordService);

        long N = 0;
        Assertions.assertThrows(InvalidParameterRangeException.class, () -> controller.top(N));
    }

    @Test
    void maximumParameterValidationTest() {
        SearchKeywordService searchKeywordService = mock(SearchKeywordService.class);
        SearchKeywordController controller = new SearchKeywordController(searchKeywordService);

        long N = 11;
        Assertions.assertThrows(InvalidParameterRangeException.class, () -> controller.top(N));
    }
}