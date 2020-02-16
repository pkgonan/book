package io.book.search.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.collect.Lists;
import io.book.common.util.TrackingIdGenerator;
import io.book.search.domain.Document;
import io.book.search.domain.Metadata;
import io.book.search.domain.Search;
import io.book.search.domain.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

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
    public SearchResult search(String query, Pageable pageable) {
        final String trackingId = TrackingIdGenerator.generateTrackingId();
        return webClient.get()
                .uri("/v1/search/book.json?query={query}&start={page}&display={size}", query, pageable.getPageNumber(), pageable.getPageSize())
                .retrieve()
                .bodyToMono(NaverSearchResult.class)
                .doOnError(e -> log.error("Failure to get books by naver api. (query : {}, pageable : {}, trackingId : {}", query, pageable, trackingId))
                .blockOptional().orElseThrow(IllegalArgumentException::new);
    }

    private static class NaverSearchResult implements SearchResult {
        @JsonUnwrapped
        private NaverMeta meta;
        @JsonProperty("items")
        private List<NaverDocument> documents;

        @Override
        public Metadata getMetadata() {
            return meta;
        }

        @Override
        public List<Document> getDocuments() {
            return Lists.newArrayList(documents);
        }
    }

    private static class NaverMeta implements Metadata {
        @JsonProperty("total")
        private Integer total;
        @JsonProperty("start")
        private Integer start;
        @JsonProperty("display")
        private Integer display;

        @Override
        public Long getTotal() {
            return Long.valueOf(total);
        }
    }

    private static class NaverDocument implements Document {
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

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getUrl() {
            return link;
        }

        @Override
        public String getIsbn() {
            return isbn;
        }

        @Override
        public String getPublishedAt() {
            return pubdate;
        }

        @Override
        public List<String> getAuthors() {
            return Lists.newArrayList(author);
        }

        @Override
        public String getPublisher() {
            return publisher;
        }

        @Override
        public List<String> getTranslators() {
            return Collections.emptyList();
        }

        @Override
        public Integer getPrice() {
            return price;
        }

        @Override
        public Integer getSalePrice() {
            return discount;
        }

        @Override
        public String getThumbnail() {
            return image;
        }

        @Override
        public String getStatus() {
            return null;
        }
    }
}