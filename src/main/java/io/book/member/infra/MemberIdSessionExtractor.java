package io.book.member.infra;

import io.book.member.exception.NotLoginMemberException;

import javax.servlet.http.HttpSession;

public final class MemberIdSessionExtractor {

    public static final String ID = "ID";

    public static long extract(final HttpSession session) {
        try {
            Object attribute = session.getAttribute(ID);
            return Long.parseLong(String.valueOf(attribute));
        } catch (Exception e) {
            throw new NotLoginMemberException();
        }
    }
}