package io.book.common.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AtTest {

    @Test
    void atCreateTest() {
        At at = At.create();
        Assertions.assertTrue(at.getCreated().equals(at.getUpdated()));
    }

    @Test
    void atUpdateTest() throws InterruptedException {
        At at = At.create();
        Thread.sleep(100);
        at.update();

        Assertions.assertTrue(at.getCreated().isBefore(at.getUpdated()));
    }
}