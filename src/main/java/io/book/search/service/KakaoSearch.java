package io.book.search.service;

import com.fasterxml.jackson.annotation.JsonProperty;
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

import java.util.List;

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
    public SearchResult search(String query, Pageable pageable) {
        final String trackingId = TrackingIdGenerator.generateTrackingId();
        return webClient.get()
                .uri("/v3/search/book?target=title&query={query}&page={page}&size={size}", query, pageable.getPageNumber(), pageable.getPageSize())
                .retrieve()
                .bodyToMono(KakaoSearchResult.class)
                .doOnError(e -> log.error("Failure to get books by kakao api. (query : {}, pageable : {}, trackingId : {}", query, pageable, trackingId))
                .blockOptional().orElseThrow(IllegalArgumentException::new);
    }

    private static class KakaoSearchResult implements SearchResult {
        @JsonProperty("meta")
        private KakaoMeta meta;
        @JsonProperty("documents")
        private List<KakaoDocument> documents;

        @Override
        public Metadata getMetadata() {
            return meta;
        }

        @Override
        public List<Document> getDocuments() {
            return Lists.newArrayList(documents);
        }
    }

    private static class KakaoMeta implements Metadata {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("pageable_count")
        private Integer pageableCount;
        @JsonProperty("is_end")
        private Boolean isEnd;

        @Override
        public Long getTotal() {
            return Long.valueOf(totalCount);
        }
    }

    private static class KakaoDocument implements Document {
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

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getDescription() {
            return contents;
        }

        @Override
        public String getUrl() {
            return url;
        }

        @Override
        public String getIsbn() {
            return isbn;
        }

        @Override
        public String getPublishedAt() {
            return datetime;
        }

        @Override
        public List<String> getAuthors() {
            return authors;
        }

        @Override
        public String getPublisher() {
            return publisher;
        }

        @Override
        public List<String> getTranslators() {
            return translators;
        }

        @Override
        public Integer getPrice() {
            return price;
        }

        @Override
        public Integer getSalePrice() {
            return salePrice;
        }

        @Override
        public String getThumbnail() {
            return thumbnail;
        }

        @Override
        public String getStatus() {
            return status;
        }
    }
}
