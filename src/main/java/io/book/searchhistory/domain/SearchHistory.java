package io.book.searchhistory.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@ToString
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(
        indexes = {
                @Index(name = "idx_search_history_1", columnList = "memberId")
        }
)
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Long memberId;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String keyword;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public static SearchHistory of(final long memberId, final String keyword) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.memberId = memberId;
        searchHistory.keyword = keyword;

        return searchHistory;
    }
}