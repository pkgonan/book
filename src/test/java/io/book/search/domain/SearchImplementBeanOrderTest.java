package io.book.search.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SearchImplementBeanOrderTest {

    @Autowired
    private List<Search> searchList;

    @Test
    void searchImplementBeanOrderTest() {
        Assertions.assertTrue(searchList.get(0) instanceof KakaoSearch);
        Assertions.assertTrue(searchList.get(1) instanceof NaverSearch);
    }
}