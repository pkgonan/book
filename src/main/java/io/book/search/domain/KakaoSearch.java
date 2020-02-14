package io.book.search.domain;

import io.book.common.util.TrackingIdGenerator;
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
                final @Value("${api.book.search:kakao:root.url}") String rootUrl) {
        this.webClient = webClientBuilder.baseUrl(rootUrl).build();
    }

    @Override
    public Page<Document> search(String query, Pageable pageable) {
        final String trackingId = TrackingIdGenerator.generateTrackingId();
        final KakaoSearchResult kakaoSearchResult = webClient.get()
                .uri("/v1/search/book?target=title&query={query}&page={page}&size={size}", query, pageable.getPageNumber(), pageable.getPageSize())
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

    private static class KakaoSearchResult {
        private KakaoMeta meta;
        private List<KakaoDocument> documents;
    }

    private static class KakaoMeta {
        private Integer totalCount;
        private Integer pageableCount;
        private Boolean isEnd;
    }

    private static class KakaoDocument {
        private String title;
        private String contents;
        private String url;
        private String isbn;
        private String datetime;
        private List<String> authors;
        private String publisher;
        private List<String> translators;
        private Integer price;
        private Integer salePrice;
        private String thumbnail;
        private String status;
    }
}
