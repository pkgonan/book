package io.book.login.service;

import io.book.member.domain.Member;
import io.book.member.domain.MemberRepository;
import io.book.member.exception.InvalidMemberException;
import io.book.member.exception.InvalidPasswordException;
import io.book.member.service.MemberParameter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final MemberRepository repository;

    LoginService(final MemberRepository repository) {
        this.repository = repository;
    }

    public long login(final MemberParameter parameter) {
        Member member = load(parameter.getUserId());
        if (!member.isValidPassword(parameter.getPassword())) {
            throw new InvalidPasswordException();
        }
        return member.getId();
    }

    private Member load(final String userId) {
        return getByUserId(userId).orElseThrow(InvalidMemberException::new);
    }

    private Optional<Member> getByUserId(final String userId) {
        return repository.findByUserId(userId);
    }
}