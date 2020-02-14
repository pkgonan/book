package io.book.member.service;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberParameter {
    @NotBlank
    private String key;
    @NotBlank
    private String password;
}
