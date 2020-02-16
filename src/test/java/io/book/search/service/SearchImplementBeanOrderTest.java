package io.book.search.service;

import integration.IntegrationTest;
import io.book.search.domain.Search;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchImplementBeanOrderTest implements IntegrationTest {

    @Autowired
    private List<Search> searchList;

    @Test
    void searchImplementBeanOrderTest() {
        Assertions.assertTrue(searchList.get(0) instanceof KakaoSearch);
        Assertions.assertTrue(searchList.get(1) instanceof NaverSearch);
    }
}