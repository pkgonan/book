package io.book.member;

import io.book.member.service.MemberDto;
import io.book.member.service.MemberParameter;
import io.book.member.service.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public void login(@RequestBody @Valid final MemberParameter parameter) {
        memberService.login(parameter);
    }

    @GetMapping("/members/{id}/login")
    public boolean isLogin(@PathVariable final Long id) {
        return memberService.isLogin(id);
    }
}