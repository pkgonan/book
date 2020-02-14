package io.book.search.domain;

import com.google.common.collect.Lists;
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
@Order(1)
@Component
class NaverSearch implements Search {

    private final WebClient webClient;

    NaverSearch(final WebClient.Builder webClientBuilder,
                final @Value("${api.book.search:naver:root.url}") String rootUrl) {
        this.webClient = webClientBuilder.baseUrl(rootUrl).build();
    }

    @Override
    public Page<Document> search(String query, Pageable pageable) {
        final String trackingId = TrackingIdGenerator.generateTrackingId();
        final NaverSearchResult naverSearchResult = webClient.get()
                .uri("/v1/search/book.json?d_titl={query}&page={page}&size={size}", query, pageable.getPageNumber(), pageable.getPageSize())
                .retrieve()
                .bodyToMono(NaverSearchResult.class)
                .doOnError(e -> log.error("Failure to get books by naver api. (query : {}, pageable : {}, trackingId : {}", query, pageable, trackingId))
                .blockOptional().orElseThrow(IllegalArgumentException::new);

        final SearchResult searchResult = toSearchResult(naverSearchResult);
        return new PageImpl<>(searchResult.getDocuments(), pageable, searchResult.getTotal());
    }

    private SearchResult toSearchResult(final NaverSearchResult naverSearchResult) {
        final Metadata metadata = toMetaData(naverSearchResult.meta);
        final List<Document> documents = toDocuments(naverSearchResult.documents);

        return new SearchResult(metadata, documents);
    }

    private Metadata toMetaData(final NaverMeta meta) {
        return Metadata.of(meta.totalCount);
    }

    private List<Document> toDocuments(final List<NaverDocument> documents) {
        return documents.stream().map(this::toDocument).collect(Collectors.toList());
    }

    private Document toDocument(final NaverDocument document) {
        return Document.of(
                document.title,
                document.description,
                document.link,
                document.isbn,
                document.pubdate,
                Lists.newArrayList(document.author),
                document.publisher,
                Lists.newArrayList(),
                document.price,
                document.discount,
                document.image,
                null);
    }

    private static class NaverSearchResult {
        private NaverMeta meta;
        private List<NaverDocument> documents;
    }

    private static class NaverMeta {
        private Integer totalCount;
        private Integer pageableCount;
        private Boolean isEnd;
    }

    private static class NaverDocument {
        private String title;
        private String description;
        private String link;
        private String isbn;
        private String pubdate;
        private String author;
        private String publisher;
        private Integer price;
        private Integer discount;
        private String image;
    }
}