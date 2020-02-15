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

    MemberService(final MemberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MemberDto join(final MemberParameter parameter) {
        if (getByUserId(parameter.getUserId()).isPresent()) {
            throw new AlreadyJoinedMemberException();
        }
        Member member = repository.save(Member.of(parameter.getUserId(), Crypto.encode(parameter.getPassword())));
        return toDto(member);
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

    private MemberDto toDto(final Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setUserId(member.getUserId());

        return dto;
    }
}