package io.book.member;

import io.book.member.service.MemberDto;
import io.book.member.service.MemberParameter;
import io.book.member.service.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}