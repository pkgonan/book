package io.book.search.domain;

import java.util.List;

public interface Document {

    String getTitle();

    String getDescription();

    String getUrl();

    String getIsbn();

    String getPublishedAt();

    List<String> getAuthors();

    String getPublisher();

    List<String> getTranslators();

    Integer getPrice();

    Integer getSalePrice();

    String getThumbnail();

    String getStatus();

}