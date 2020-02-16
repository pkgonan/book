package io.book.search.domain;

import com.google.common.collect.Lists;

import java.time.OffsetDateTime;
import java.util.List;

abstract class SearchTest {

    protected static SearchResult generateSearchResult() {
        return new TestSearchResult();
    }

    private static class TestSearchResult implements SearchResult {

        @Override
        public Metadata getMetadata() {
            return () -> 2L;
        }

        @Override
        public List<Document> getDocuments() {
            return Lists.newArrayList(new TestDocument(), new TestDocument());
        }
    }

    private static class TestDocument implements Document {

        @Override
        public String getTitle() {
            return "자바를 잡아라...";
        }

        @Override
        public String getDescription() {
            return "테스트";
        }

        @Override
        public String getUrl() {
            return "https://www.google.com";
        }

        @Override
        public String getIsbn() {
            return "ISBN-12345";
        }

        @Override
        public String getPublishedAt() {
            return OffsetDateTime.now().toString();
        }

        @Override
        public List<String> getAuthors() {
            return Lists.newArrayList("김민규");
        }

        @Override
        public String getPublisher() {
            return "대형 출판사";
        }

        @Override
        public List<String> getTranslators() {
            return Lists.newArrayList();
        }

        @Override
        public Integer getPrice() {
            return 50000;
        }

        @Override
        public Integer getSalePrice() {
            return 30000;
        }

        @Override
        public String getThumbnail() {
            return "test";
        }

        @Override
        public String getStatus() {
            return "정상판매";
        }
    }
}