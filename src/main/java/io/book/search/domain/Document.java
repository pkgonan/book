package io.book.search.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Document {
    private String title;
    private String description;
    private String url;
    private String isbn;
    private String publishAt;
    private List<String> authors;
    private String publisher;
    private List<String> translators;
    private Integer price;
    private Integer salePrice;
    private String thumbnail;
    private String status;

    private Document() {}

    public static Document of(final String title,
                              final String description,
                              final String url,
                              final String isbn,
                              final String publishAt,
                              final List<String> authors,
                              final String publisher,
                              final List<String> translators,
                              final Integer price,
                              final Integer salePrice,
                              final String thumbnail,
                              final String status) {

        Document document = new Document();
        document.title = title;
        document.description = description;
        document.url = url;
        document.isbn = isbn;
        document.publishAt = publishAt;
        document.authors = authors;
        document.publisher = publisher;
        document.translators = translators;
        document.price = price;
        document.salePrice = salePrice;
        document.thumbnail = thumbnail;
        document.status = status;

        return document;
    }
}