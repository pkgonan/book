package io.book.member.domain;

import io.book.common.domain.At;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(
        indexes = {
                @Index(name = "idx_member_1", columnList = "userId")
        }
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String userId;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Embedded
    private At at;

    public static Member of(final String userId, final String password) {
        Member member = new Member();
        member.userId = userId;
        member.password = password;
        member.at = At.create();

        return member;
    }

    public void edit(final String password) {
        this.password = password;
        this.at.update();
    }

    public boolean isValidPassword(final String password) {
        return Crypto.checkPassword(password, this.password);
    }
}