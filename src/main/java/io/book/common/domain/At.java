package io.book.common.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@ToString
@Getter
@EqualsAndHashCode
@Embeddable
public class At {

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime created;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updated;

    private At() {
    }

    public static At create() {
        At at = new At();
        at.created = OffsetDateTime.now();
        at.updated = at.created;
        return at;
    }

    public void update() {
        updated = OffsetDateTime.now();
    }
}
