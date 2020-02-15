package io.book.member.infra;

import io.book.member.exception.NotLoginMemberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class MemberIdSessionExtractorTest {

    @Test
    void extractMemberIdTest() {
        HttpSession session = mock(HttpSession.class);
        doReturn(null).when(session).getAttribute(MemberIdSessionExtractor.ID);

        Assertions.assertThrows(NotLoginMemberException.class,
                () -> MemberIdSessionExtractor.extract(session));
    }
}