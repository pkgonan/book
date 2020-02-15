package io.book.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import io.book.common.util.TrackingIdGenerator;
import lombok.Data;
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
                final @Value("${api.book.search.naver.root.url}") String rootUrl) {
        this.webClient = webClientBuilder
                .baseUrl(rootUrl)
                .defaultHeader("X-Naver-Client-Id", "zaCyFz3_5CWy03z9YwLo")
                .defaultHeader("X-Naver-Client-Secret", "1y0KsMqXvQ")
                .build();
    }

    @Override
    public Page<Document> search(String query, Pageable pageable) {
        final String trackingId = TrackingIdGenerator.generateTrackingId();
        final NaverSearchResult naverSearchResult = webClient.get()
                .uri("/v1/search/book.json?query={query}&start={page}&display={size}", query, pageable.getPageNumber(), pageable.getPageSize())
                .retrieve()
                .bodyToMono(NaverSearchResult.class)
                .doOnError(e -> log.error("Failure to get books by naver api. (query : {}, pageable : {}, trackingId : {}", query, pageable, trackingId))
                .blockOptional().orElseThrow(IllegalArgumentException::new);

        final SearchResult searchResult = toSearchResult(naverSearchResult);
        return new PageImpl<>(searchResult.getDocuments(), pageable, searchResult.getTotal());
    }

    private SearchResult toSearchResult(final NaverSearchResult naverSearchResult) {
        final Metadata metadata = toMetaData(naverSearchResult);
        final List<Document> documents = toDocuments(naverSearchResult.documents);

        return new SearchResult(metadata, documents);
    }

    private Metadata toMetaData(final NaverSearchResult searchResult) {
        return Metadata.of(searchResult.getTotal());
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

    @Data
    private static class NaverSearchResult {
        @JsonProperty("total")
        private Integer total;
        @JsonProperty("start")
        private Integer start;
        @JsonProperty("display")
        private Integer display;
        @JsonProperty("items")
        private List<NaverDocument> documents;
    }

    @Data
    private static class NaverDocument {
        @JsonProperty("title")
        private String title;
        @JsonProperty("description")
        private String description;
        @JsonProperty("link")
        private String link;
        @JsonProperty("isbn")
        private String isbn;
        @JsonProperty("pubdate")
        private String pubdate;
        @JsonProperty("author")
        private String author;
        @JsonProperty("publisher")
        private String publisher;
        @JsonProperty("price")
        private Integer price;
        @JsonProperty("discount")
        private Integer discount;
        @JsonProperty("image")
        private String image;
    }
}