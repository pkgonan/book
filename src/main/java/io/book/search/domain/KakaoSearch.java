package io.book.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.book.common.util.TrackingIdGenerator;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Order(0)
@Component
class KakaoSearch implements Search {

    private final WebClient webClient;

    KakaoSearch(final WebClient.Builder webClientBuilder,
                final @Value("${api.book.search.kakao.root.url}") String rootUrl) {
        this.webClient = webClientBuilder
                .baseUrl(rootUrl)
                .defaultHeader("Authorization", "KakaoAK c8b1cbac2461de1e09f561913d085b24")
                .build();
    }

    @Override
    public Page<Document> search(String query, Pageable pageable) {
        final String trackingId = TrackingIdGenerator.generateTrackingId();
        final KakaoSearchResult kakaoSearchResult = webClient.get()
                .uri("/v3/search/book?target=title&query={query}&page={page}&size={size}", query, pageable.getPageNumber(), pageable.getPageSize())
                .retrieve()
                .bodyToMono(KakaoSearchResult.class)
                .doOnError(e -> log.error("Failure to get books by kakao api. (query : {}, pageable : {}, trackingId : {}", query, pageable, trackingId))
                .blockOptional().orElseThrow(IllegalArgumentException::new);

        final SearchResult searchResult = toSearchResult(kakaoSearchResult);
        return new PageImpl<>(searchResult.getDocuments(), pageable, searchResult.getTotal());
    }

    private SearchResult toSearchResult(final KakaoSearchResult kakaoSearchResult) {
        final Metadata metadata = toMetaData(kakaoSearchResult.meta);
        final List<Document> documents = toDocuments(kakaoSearchResult.documents);

        return new SearchResult(metadata, documents);
    }

    private Metadata toMetaData(final KakaoMeta meta) {
        return Metadata.of(meta.totalCount);
    }

    private List<Document> toDocuments(final List<KakaoDocument> documents) {
        return documents.stream().map(this::toDocument).collect(Collectors.toList());
    }

    private Document toDocument(final KakaoDocument document) {
        return Document.of(
                document.title,
                document.contents,
                document.url,
                document.isbn,
                document.datetime,
                document.authors,
                document.publisher,
                document.translators,
                document.price,
                document.salePrice,
                document.thumbnail,
                document.status);
    }

    @Setter
    @Data
    private static class KakaoSearchResult {
        @JsonProperty("meta")
        private KakaoMeta meta;
        @JsonProperty("documents")
        private List<KakaoDocument> documents;
    }


    @Data
    private static class KakaoMeta {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("pageable_count")
        private Integer pageableCount;
        @JsonProperty("is_end")
        private Boolean isEnd;
    }

    @Data
    private static class KakaoDocument {
        @JsonProperty("title")
        private String title;
        @JsonProperty("contents")
        private String contents;
        @JsonProperty("url")
        private String url;
        @JsonProperty("isbn")
        private String isbn;
        @JsonProperty("datetime")
        private String datetime;
        @JsonProperty("authors")
        private List<String> authors;
        @JsonProperty("publisher")
        private String publisher;
        @JsonProperty("translators")
        private List<String> translators;
        @JsonProperty("price")
        private Integer price;
        @JsonProperty("sale_price")
        private Integer salePrice;
        @JsonProperty("thumbnail")
        private String thumbnail;
        @JsonProperty("status")
        private String status;
    }
}
