package io.book.login;

import io.book.login.service.LoginService;
import io.book.member.infra.MemberIdSessionExtractor;
import io.book.member.service.MemberParameter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Validated
@RestController
public class LoginController {

    private final LoginService loginService;

    LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid final MemberParameter parameter, final HttpSession session) {
        final long loginId = loginService.login(parameter);
        session.setAttribute(MemberIdSessionExtractor.ID, loginId);
    }

    @PostMapping("/logout")
    public void logout(final HttpSession httpSession) {
        httpSession.invalidate();
    }
}