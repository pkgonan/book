package io.book.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class MemberTest {

    @Test
    void create() {
        Member member = Member.of("id", "password");

        Assertions.assertEquals("id", member.getUserId());
        Assertions.assertEquals("password", member.getPassword());
        Assertions.assertNotNull(member.getAt());

        OffsetDateTime createdAt = member.getAt().getCreated();
        OffsetDateTime updatedAt = member.getAt().getUpdated();
        Assertions.assertNotNull(createdAt);
        Assertions.assertNotNull(updatedAt);
        Assertions.assertEquals(createdAt, updatedAt);
    }

    @Test
    void update() throws InterruptedException {
        Member member = Member.of("id", "password");
        Thread.sleep(100);
        member.edit("password1");

        Assertions.assertNotNull(member.getAt());

        OffsetDateTime createdAt = member.getAt().getCreated();
        OffsetDateTime updatedAt = member.getAt().getUpdated();

        Assertions.assertNotNull(createdAt);
        Assertions.assertNotNull(updatedAt);
        Assertions.assertNotEquals(createdAt, updatedAt);
    }

    @Test
    void isValidPassword() {
        String password = "password";
        String encodedPassword = Crypto.encode(password);
        Member member = Member.of("id", encodedPassword);

        boolean validPassword = member.isValidPassword(password);
        Assertions.assertTrue(validPassword);
    }

    @Test
    void isInvalidPassword() {
        String password = "password";
        String encodedPassword = Crypto.encode(password);
        Member member = Member.of("id", encodedPassword);

        boolean validPassword = member.isValidPassword("password1");
        Assertions.assertFalse(validPassword);
    }
}