package io.book.member;

import io.book.member.exception.AlreadyLoginMemberException;
import io.book.member.infra.MemberIdSessionExtractor;
import io.book.member.service.MemberDto;
import io.book.member.service.MemberParameter;
import io.book.member.service.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Validated
@RestController
public class MemberController {

    private final MemberService memberService;

    MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public MemberDto join(@RequestBody @Valid final MemberParameter parameter) {
        return memberService.join(parameter);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid final MemberParameter parameter, final HttpSession session) {
        final long loginId = memberService.login(parameter);
        if (!session.isNew()) {
            throw new AlreadyLoginMemberException();
        }
        session.setAttribute(MemberIdSessionExtractor.ID, loginId);
    }

    @PostMapping("/logout")
    public void logout(final HttpSession httpSession) {
        httpSession.invalidate();
    }
}