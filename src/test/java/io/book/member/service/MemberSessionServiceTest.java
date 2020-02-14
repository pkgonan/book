package io.book.member.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberSessionServiceTest {

    @Test
    void getSessionKey() {
        final long id = 1000L;
        final String sessionKey = MemberSessionService.getSessionKey(id);

        Assertions.assertEquals("sessions:1000", sessionKey);
    }
}