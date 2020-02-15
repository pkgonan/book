package io.book.member.service;

import io.book.member.domain.Crypto;
import io.book.member.domain.Member;
import io.book.member.domain.MemberRepository;
import io.book.member.exception.AlreadyJoinedMemberException;
import io.book.member.exception.InvalidMemberException;
import io.book.member.exception.InvalidPasswordException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository repository;
    private final MemberSessionService sessionService;

    MemberService(final MemberRepository repository,
                  final MemberSessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }

    @Transactional
    public MemberDto join(final MemberParameter parameter) {
        if (getByKey(parameter.getKey()).isPresent()) {
            throw new AlreadyJoinedMemberException();
        }
        Member member = repository.save(Member.of(parameter.getKey(), Crypto.encode(parameter.getPassword())));
        return toDto(member);
    }

    public void login(final MemberParameter parameter) {
        Member member = load(parameter.getKey());
        if (!member.isValidPassword(parameter.getPassword())) {
            throw new InvalidPasswordException();
        }
        sessionService.set(member.getId());
    }

    public boolean isLogin(final long id) {
        return sessionService.isExist(id);
    }

    private Member load(final String key) {
        return getByKey(key).orElseThrow(InvalidMemberException::new);
    }

    private Optional<Member> getByKey(final String key) {
        return repository.findByKey(key);
    }

    private MemberDto toDto(final Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setKey(member.getKey());

        return dto;
    }
}